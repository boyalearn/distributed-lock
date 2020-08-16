package com.distributed.lock;

import com.distributed.exception.LockTimeOutException;

public interface Lock {
    boolean lock(String lockName, int time) throws LockTimeOutException;

    void unLock(String lockName);

    boolean tryLock(String lockName);
}
