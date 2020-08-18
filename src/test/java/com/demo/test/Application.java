package com.demo.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import com.demo.test.service.DBLockTestService;
import com.distributed.exception.LockTimeOutException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.distributed.annotation.EnableDistributedLock;


@EnableDistributedLock
@SpringBootApplication
public class Application {

	static ExecutorService pool = Executors.newFixedThreadPool(4);


	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException, LockTimeOutException {
		ConfigurableApplicationContext context=SpringApplication.run(Application.class,args);
		DBLockTestService service = context.getBean(DBLockTestService.class);

		/*for (int i = 0; i < 100; i++) {
			pool.execute(new Runnable() {

				@Override
				public void run() {
					service.sayHello("Bob");
				}
			});
		}
		try {
			Thread.sleep(2000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		for(int i=0;i<100;i++){
			new Thread(()->service.sayHello("Bob")).start();
		}
	}


}
