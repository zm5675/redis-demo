package com.hmdp.utils;

public interface ILock {

    /**
     * 获取分布式锁操作（在redis中设置互斥key）
     * @param timeOutSec
     * @return
     */
    boolean tryLock(long timeOutSec);

    /**
     * 释放锁
     */
    void unLock();
}
