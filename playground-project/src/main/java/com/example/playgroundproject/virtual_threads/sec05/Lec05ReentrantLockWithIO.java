package com.example.playgroundproject.virtual_threads.sec05;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Lec05ReentrantLockWithIO {
    private static final Lock lock = new ReentrantLock();
    private static final List<Integer> list = new ArrayList<>();
    public static void main(String[] args) {
        demo2();
    }

    public static void ioTask(){
        try{
            lock.lock();
            Util.sleepSeconds(10);
        }catch(Exception e){
            log.error(e.getMessage());
        }finally{
            lock.unlock();
        }

    }

    private static void demo(Thread.Builder builder){
        for (int i = 0; i <50; i++) {
            builder.start(()->{
                log.info("TASK STARTED: {}", Thread.currentThread());
                ioTask();
                log.info("TASK ENDED: {}", Thread.currentThread());
            });
        }
        Util.sleepSeconds(15);
    }


    private static void demo1(){
        demo(Thread.ofPlatform());
    }


    //now 50 threads will be started immediately!
    private static void demo2(){
        demo(Thread.ofVirtual());
    }
}
