package com.distributed.aspect;

import com.distributed.LockService;
import com.distributed.annotation.DistributedLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;


@Aspect
public class DistributedLockAspect implements ApplicationContextAware {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private ApplicationContext applicationContext;

    @Pointcut("@annotation(com.distributed.annotation.DistributedLock)")
    private void aspectJMethod() {
    }


    /**
     * 开始执行分布式锁逻辑。
     *
     * @param processor
     * @return
     */
    @Around(value = "aspectJMethod()")
    public Object doConcurrentOperation(ProceedingJoinPoint processor) throws Throwable {
        //首先获取同步方法的注解。
        DistributedLock annotation = getAnnotation(getMethod(processor));
        //通过注解获取实现的服务类型
        LockService lockService = selectLockService(annotation);
        LOGGER.debug("start do lock logic..");
        //调用服务
        return lockService.doService(processor, annotation);
    }


    private LockService selectLockService(DistributedLock annotation) {
        LockService bean = (LockService) applicationContext.getBean(annotation.service());
        return bean;
    }

    /**
     * 通过ProceedingJoinPoint对应的实例获取到当前执行方法
     *
     * @param processor
     * @return
     */
    private Method getMethod(ProceedingJoinPoint processor) {
        Signature signature = processor.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return methodSignature.getMethod();
    }

    /**
     * 获取到方法上对应的DistributedLock注解
     *
     * @param method
     * @return
     */
    private DistributedLock getAnnotation(Method method) {
        return method.getAnnotation(DistributedLock.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

}
