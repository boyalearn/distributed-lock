package com.distributed.config;

import com.distributed.DBLockService;
import com.distributed.LockService;
import com.distributed.aspect.DistributedLockAspect;
import com.distributed.lock.DBLock;
import com.distributed.lock.Lock;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.distributed.dao"})
public class DistributedConfig {

    @Bean
    public LockService lockService() {
        return new DBLockService();
    }

    @Bean
    public DistributedLockAspect distributedLockAspect() {
        return new DistributedLockAspect();
    }

    @Bean
    public Lock lock() {
        return new DBLock();
    }


}
