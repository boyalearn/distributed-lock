package com.distributed.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.distributed.config.DistributedConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Configuration
@Import(DistributedConfig.class)
public @interface EnableDistributedLock {

}
