package com.demo.test.service;

import com.distributed.annotation.DistributedLock;
import com.distributed.bean.LockBean;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DBLockTestService {

    private volatile int index = 0;

    @DistributedLock
    public String sayHello(String content) {
        System.out.println("call for " + index);
        Long id = Thread.currentThread().getId();
        try {

            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Random ra = new Random();
        int random = ra.nextInt(10);
        String result="Hello," + content + index;
        ++index;
        return result;

    }

    @DistributedLock
    public String sayHello(String content, LockBean lockBean) {
        Long id = Thread.currentThread().getId();
        System.err.println("Thread ID:" + id + ":" + lockBean.getLockName());
        try {

            Thread.sleep(10000);
            System.out.println("Thread ID:" + id + ":" + lockBean.getLockName());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Random ra = new Random();
        int random = ra.nextInt(10);
        if (random > 8) {
            throw new NullPointerException();
        }
        System.err.println("Thread ID:" + id + ":" + lockBean.getLockName());
        return "Hello," + content;

    }

}
