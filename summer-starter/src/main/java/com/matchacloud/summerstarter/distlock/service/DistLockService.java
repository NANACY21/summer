package com.matchacloud.summerstarter.distlock.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.matchacloud.summerstarter.distlock.dto.DistLock;
import com.matchacloud.summerstarter.distlock.mapper.DistLockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lichi
 */
@Service
public class DistLockService {

    @Autowired
    private DistLockMapper distLockMapper;

    // 1. 插入
    public void insert(DistLock lock) {
        distLockMapper.insert(lock);
    }

    // 2. 根据条件查询
    public List<DistLock> queryByLockKey(String lockKey) {
        LambdaQueryWrapper<DistLock> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DistLock::getLockKey, lockKey);
        return distLockMapper.selectList(wrapper);
    }

    // 3. 根据条件删除
    public int deleteByLockKeyAndLockOwner(String lockKey,String lockOwner) {
        LambdaQueryWrapper<DistLock> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DistLock::getLockKey, lockKey)
                .eq(DistLock::getLockOwner, lockOwner);
        return distLockMapper.delete(wrapper);
    }

    // 4. 根据 lockKey 和过期时间删除（对应你的 SQL）
    public int deleteByLockKeyAndExpireTime(String lockKey) {
        LambdaQueryWrapper<DistLock> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DistLock::getLockKey, lockKey)
                .lt(DistLock::getExpireTime, new Date());
        return distLockMapper.delete(wrapper);
    }

    // 4. 根据条件修改
    public int updateByLockOwner(String lockOwner,String lockKey, Date newExpireTime) {
        LambdaUpdateWrapper<DistLock> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(DistLock::getLockOwner, lockOwner)
                .eq(DistLock::getLockKey,lockKey)
                .set(DistLock::getExpireTime, newExpireTime);
        return distLockMapper.update(null, wrapper);
    }
}

