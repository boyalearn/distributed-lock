package com.distributed;

import com.distributed.annotation.DistributedLock;
import org.aspectj.lang.ProceedingJoinPoint;

public interface LockService {
    Object doService(ProceedingJoinPoint processor, DistributedLock annotation) throws Throwable;

}
