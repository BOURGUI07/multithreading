package com.example.playgroundproject.virtual_threads.sec05;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Lec04ReentrantLock {
    private static final List<Integer> list = new ArrayList<>();
    private static final Lock lock = new ReentrantLock();
    public static void main(String[] args) {
        demo1();
    }

    public static void inMemoryTask(){
        try{
            lock.lock();
            list.add(1);
        }catch(Exception e){
            log.error(e.getMessage());
        }finally{
            lock.unlock();
        }
    }

    private static void demo(Thread.Builder builder){
        for (int i = 0; i <50; i++) {
            builder.start(()->{
                for (int j = 0; j < 200; j++) {
                    inMemoryTask();
                }
            });
        }
        Util.sleepSeconds(2);
        log.info("list size: {}", list.size());
    }

    //after adding the lock

    //the list size is always 10000
    private static void demo1(){
        demo(Thread.ofPlatform());
    }

    //the list size is always  10000
    private static void demo2(){
        demo(Thread.ofVirtual());
    }
}
