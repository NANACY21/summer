package com.matchacloud.summerstarter.distlock;

import com.matchacloud.summerstarter.distlock.dto.DistLock;
import com.matchacloud.summerstarter.distlock.service.DistLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 分布式锁只基于mysql的实现
 * <p>
 * 技术背景：集群多实例下，同一业务逻辑同一时间只允许一个线程执行，其余阻塞
 * 技术原理：基于mysql唯一索引 + 插入失败抢锁 同一锁标识只能插入一条数据，先插入成功的线程拿到锁；插入失败的线程抢锁失败，循环重试（阻塞）
 * 能规避的问题：(分布式锁无法做到100%无错误完全健壮)
 * 1.锁续期：解决业务还没结束锁过期
 * 2.锁在业务结束没释放成功、锁误删
 * 3.jvm宕机、mysql宕机的容错
 * 锁记录保留在 MySQL，expire_time 会超时。其他线程抢锁时，先执行 clearExpiredLock 删除过期锁，再正常抢锁；JVM 重启后，若锁未过期则等待自动过期
 * 4.仅一个mysql实例 不存在主从同步分布式锁的问题
 * 改进建议：
 * 1.获取锁失败不一直阻塞 30秒还是获取锁失败前端返回稍后再试
 * 2.根据业务实际耗时设置过期时间
 */
@Component
public class DistributeLockUtil {

    // ====================== 配置项（可根据业务调整）======================
    /**
     * 锁默认持有超时时间（初始过期时长，单位：秒）
     * =预估的业务耗时
     */
    private static final int LOCK_EXPIRE_SECONDS = 300;
    /**
     * 锁续期间隔（单位：秒），必须小于过期时长
     */
    private static final int RENEW_INTERVAL_SECONDS = 120;
    /**
     * 抢锁重试休眠时间（单位：毫秒）
     */
    private static final long LOCK_RETRY_SLEEP_MS = 1000;
    /**
     * 释放锁最大重试次数（应对网络抖动）
     */
    private static final int UNLOCK_RETRY_TIMES = 3;
    /**
     * 全局线程池：统一管理续期线程，避免线程爆炸
     */
    private static final ScheduledExecutorService RENEW_POOL;

    static {
        // 初始化单例线程池，核心线程10，守护线程（JVM退出自动销毁）
        RENEW_POOL = Executors.newScheduledThreadPool(10, r -> {
            Thread t = new Thread(r, "lock-renew-thread");
            t.setDaemon(true);
            return t;
        });
    }


    /**
     * 锁唯一标识
     * 同一业务必须使用同一个 lockKey，不同业务使用不同 lockKey；
     */
    private String lockKey;
    /**
     * 锁持有者全局唯一ID（当前实例+线程标识）
     */
    private String lockOwner;
    /**
     * 标记当前锁是否持有中
     */
    private final AtomicBoolean lockHeld = new AtomicBoolean(false);
    /**
     * 续期任务句柄，用于停止续期
     */
    private ScheduledFuture<?> renewFuture;
    /**
     * 锁记录表curd操作
     */
    @Autowired
    private DistLockService distLockService;

    /**
     * 静态工厂方法：创建分布式锁实例
     */
    public static DistributeLockUtil create(String lockKey, DistLockService distLockService) {
        DistributeLockUtil util = new DistributeLockUtil();
        util.lockKey = lockKey;
        // 生成全局唯一持有者ID：UUID 保证集群多实例/线程不重复 严格保证 lock_owner 全局唯一
        util.lockOwner = UUID.randomUUID().toString().replace("-", "");
        util.distLockService = distLockService;
        return util;
    }

    // ====================== 核心API：加锁（阻塞重试）======================

    /**
     * 阻塞式加锁：循环重试，直到获取锁成功
     * jvm如果宕机 锁没释放 其他线程占用锁时删除过期锁再加锁,重启jvm后如果锁还没过期 等到过期即可 然后会加锁成功
     * mysql宕机 根据过期时间 其他线程占用锁时删除过期锁再加锁
     */
    public boolean lock() {
        if (lockHeld.get()) {
            return true;
        }
        while (true) {
            // 步骤1：清理当前key的过期锁（解决宕机死锁）
            clearExpiredLock();
            // 步骤2：尝试插入数据抢锁（唯一索引冲突则抢锁失败）
            boolean lockSuccess = tryInsertLock();
            if (lockSuccess) {
                lockHeld.set(true);
                // 步骤3：加锁成功，启动后台续期线程 续期线程核心要解决业务还在进行但是锁过期的问题 避免多次都续期失败
                startLockRenew();
                return true;
            }
            // 抢锁失败，休眠后重试
            try {
                Thread.sleep(LOCK_RETRY_SLEEP_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
    }

    // ====================== 核心API：释放锁（finally执行 + 重试 + 仅持有者可删）======================

    /**
     * 业务执行完成后(结束、中途异常)释放锁 必须放在finally块中执行
     */
    public void unlock() {
        if (!lockHeld.get()) {
            return;
        }
        // 停止续期任务
        stopLockRenew();
        // 重试删除锁，应对网络超时/瞬时异常
        boolean deleteSuccess = false;
        //删锁增加重试机制（应对瞬时网络 / 超时）
        for (int i = 0; i < UNLOCK_RETRY_TIMES; i++) {
            try {
                //仅持有者才能删锁，避免误删别人的锁
                deleteLockByOwner();
                deleteSuccess = true;
                break;
            } catch (Exception e) {
                // 重试间隔
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        // 标记锁释放
        lockHeld.set(false);
        if (!deleteSuccess) {
            System.err.println("锁释放失败，lockKey=" + lockKey + "，等待锁自动过期");
        }
    }

    // ====================== 内部方法：清理过期锁 ======================

    /**
     * 删除当前lock_key下 已过期的锁记录
     */
    private void clearExpiredLock() {
        distLockService.deleteByLockKeyAndExpireTime(lockKey);
    }

    // ====================== 内部方法：插入抢锁 ======================

    /**
     * 插入锁记录，插入成功=获锁，唯一索引冲突=抢锁失败
     */
    private boolean tryInsertLock() {
        DistLock distLock = new DistLock();
        distLock.setLockKey(lockKey);
        distLock.setLockOwner(lockOwner);
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(LOCK_EXPIRE_SECONDS);
        distLock.setExpireTime(Timestamp.valueOf(expireTime));
        try {
            //插入数据时候是6点35 过期时间=6点40
            distLockService.insert(distLock);
        } catch (Exception e) {
            //索引冲突 或返回0
            return false;
        }
        return true;
    }

    // ====================== 内部方法：锁续期（定时延长过期时间）======================

    /**
     * 启动锁续期任务：定时更新expire_time，防止业务未完成锁过期
     */
    private void startLockRenew() {
        if (renewFuture != null && !renewFuture.isDone()) {
            return;
        }
        // 固定频率执行续期
        renewFuture = RENEW_POOL.scheduleAtFixedRate(() -> {
            // 锁已释放，终止续期
            if (!lockHeld.get()) {
                return;
            }
            renewLockExpireTime();
        }, RENEW_INTERVAL_SECONDS, RENEW_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    /**
     * 更新锁过期时间：仅当前持有者可续期
     * 为了防止多次续期失败 导致 锁过期业务还在执行此时其他线程拿到锁执行->并发问题
     * 增加续期失败重试 也可以更频繁的续期
     */
    private void renewLockExpireTime() {
        boolean success = false;

        // 最多重试3次
        for (int i = 0; i < 3 && !success; i++) {
            try {
                LocalDateTime newExpire = LocalDateTime.now().plusSeconds(LOCK_EXPIRE_SECONDS);
                int updated = distLockService.updateByLockOwner(lockOwner, lockKey, Timestamp.valueOf(newExpire));

                if (updated > 0) {
                    success = true;
                    System.out.println("续期成功，新到期时间：" + newExpire);
                } else {
                    System.err.println("续期失败：锁可能已被删除或持有者不匹配");
                }
            } catch (Exception e) {
                System.err.println("续期异常（第" + (i + 1) + "次尝试）：" + e.getMessage());

                if (i == 2) {
                    // 3次都失败，主动释放锁避免死锁
                    System.err.println("续期3次均失败，主动释放锁");
                    //???
                    //lockHeld.set(false);
                }
            }

            // 重试间隔 500ms
            if (!success && i < 2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }


    /**
     * 停止续期任务
     */
    private void stopLockRenew() {
        if (renewFuture != null) {
            renewFuture.cancel(false);
            renewFuture = null;
        }
    }

    // ====================== 内部方法：释放锁（仅持有者删除）======================

    /**
     * 根据lock_key + lock_owner 删除锁，防止误删他人锁
     */
    private void deleteLockByOwner() throws SQLException {
        distLockService.deleteByLockKeyAndLockOwner(lockKey, lockOwner);
    }

    // ====================== 关闭全局线程池（项目销毁时调用）======================
    public static void shutdownPool() {
        RENEW_POOL.shutdown();
    }
}
