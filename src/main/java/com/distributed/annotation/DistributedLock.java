package com.distributed.annotation;

import com.distributed.DBLockService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {

    Class<?> service() default DBLockService.class;

    String name() default "lock";

    int timeOut() default 60000;
}
