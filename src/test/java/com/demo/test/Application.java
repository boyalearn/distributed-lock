package com.demo.test;

import com.demo.test.service.DBLockTestService;
import com.distributed.annotation.EnableDistributedLock;
import com.distributed.exception.LockTimeOutException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


@EnableDistributedLock
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException, LockTimeOutException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        DBLockTestService service = context.getBean(DBLockTestService.class);

        for (int i = 0; i < 100; i++) {
            new Thread(() -> service.sayHello("Bob")).start();
        }
    }


}
