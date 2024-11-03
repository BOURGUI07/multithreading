package com.example.playgroundproject.executor_service.sec07;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;

@Slf4j
public class Lec01AutoCloseable {
    public static void main(String[] args) {
        demo1();
    }

    public static void task(){
        Util.sleepSeconds(1);
        log.info("task executed");
    }

    private static void demo1(){
        var executorService = Executors.newSingleThreadExecutor();
        executorService.submit(Lec01AutoCloseable::task);
        log.info("task submitted");

        /*
            16:34:40.779 [main] INFO com.example.playgroundproject.executor_service.sec07.Lec01AutoCloseable -- task submitted
16:34:41.790 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec01AutoCloseable -- task executed
         */

        // it will keep running
    }

    private static void demo2(){
        var executorService = Executors.newSingleThreadExecutor();
        executorService.submit(Lec01AutoCloseable::task);
        log.info("task submitted");
        executorService.shutdown();
        // now it will stop running after executing and submitting the task
        // the shutdown() will wait for the task until it executes, the shutdownNow() will submit but will exit immediately
        // now the shutdown method won't allow us to submit new tasks!
        // if you try to submit a new task after writing shutdown, it will throw exception
    }

    private static void demo3(){
        try(var executorService = Executors.newSingleThreadExecutor()) {
            executorService.submit(Lec01AutoCloseable::task);
            log.info("task submitted");
        }
        // it will submit and execute the task
        // we don't have to shut it down ourselves, since the close() method in autocloseable will take care of it
    }





}
