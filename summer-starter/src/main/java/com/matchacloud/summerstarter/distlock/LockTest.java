package com.matchacloud.summerstarter.distlock;

/**
 * DistributeLockUtil分布式锁使用方式
 */
public class LockTest {

    // 全局唯一业务锁Key（不同业务使用不同key）
    private static final String BUSINESS_LOCK_KEY = "order:pay:lock_001";

    public static void main(String[] args) {
        // 模拟集群多个线程/实例并发抢锁
        for (int i = 0; i < 5; i++) {
            new Thread(LockTest::businessTask, "biz-thread-" + i).start();
        }
    }

    /**
     * 需互斥执行的业务逻辑
     */
    private static void businessTask() {
        // 1. 创建分布式锁实例
        DistributeLockUtil lock = DistributeLockUtil.create(BUSINESS_LOCK_KEY, null);
        try {
            // 2. 阻塞加锁，获取成功才往下执行
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " 获取到分布式锁，开始执行业务");

            // ====================== 你的互斥业务逻辑 ======================
            // 模拟长耗时业务（超过初始过期时间，测试续期效果）
            try {
                Thread.sleep(400 * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // ===========================================================

            System.out.println(Thread.currentThread().getName() + " 业务执行完成");
        } finally {
            // 3. 【强制】finally中释放锁，保证无论正常/异常都释锁
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + " 已释放分布式锁");
        }
    }
}
