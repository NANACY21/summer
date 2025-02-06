package com.matchacloud.basic.thread.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock是可重入锁 公平锁/非公平锁 排他锁(悲观锁)
 */
public class ReentrantLockTest {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
    }

}
