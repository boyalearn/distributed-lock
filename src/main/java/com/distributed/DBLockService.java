package com.distributed;


import com.distributed.annotation.DistributedLock;
import com.distributed.exception.NoOpException;
import com.distributed.lock.Lock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DBLockService implements LockService {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Lock lock;


    @Override
    public Object doService(ProceedingJoinPoint processor, DistributedLock annotation) throws Throwable {
        boolean result = lock.lock(annotation.name(), annotation.timeOut());
        if (result) {
            LOGGER.debug("get lock success");
            try {
                return processor.proceed(processor.getArgs());
            } finally {
                LOGGER.debug("release lock success");
                lock.unLock(annotation.name());
            }
        }
        throw new NoOpException();
    }


}
