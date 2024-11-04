package com.example.playgroundproject.completabale_future.sec08;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Slf4j
public class Lec02RunAsync {
    public static void main(String[] args) {
        demo5();
    }

    private static void runAsync() {
        log.info("runAsync started");
        CompletableFuture.runAsync(()->{
            Util.sleepSeconds(1);
            log.info("runAsync completed");
        });
        log.info("runAsync ended");
    }

    private static void runAsyncWithVirtualThread() {
        log.info("runAsync started");
        CompletableFuture.runAsync(()->{
            Util.sleepSeconds(1);
            log.info("runAsync completed");
        }, Executors.newVirtualThreadPerTaskExecutor());
        log.info("runAsync ended");
    }

    private static CompletableFuture<Void> runAsyncWithVirtualThread1() {
        log.info("runAsync started");
        var cf = CompletableFuture.runAsync(()->{
            Util.sleepSeconds(1);
            log.info("runAsync completed");
        }, Executors.newVirtualThreadPerTaskExecutor());
        log.info("runAsync ended");
        return cf;
    }

    private static CompletableFuture<Void> runAsyncWithVirtualThread2() {
        log.info("runAsync started");
        var cf = CompletableFuture.runAsync(()->{
            Util.sleepSeconds(1);
            throw new RuntimeException("oops!");
        }, Executors.newVirtualThreadPerTaskExecutor());
        log.info("runAsync ended");
        return cf;
    }


    private static void demo(){
        log.info("demo started");
        runAsync();
        log.info("demo ended");
        Util.sleepSeconds(2);

        /*
            07:39:51.931 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- demo started
07:39:51.934 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- runAsync started
07:39:51.938 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- runAsync ended
07:39:51.938 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- demo ended
07:39:52.955 [ForkJoinPool.commonPool-worker-1] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- runAsync completed

         */

        //it is using platform thread to run the task (ForkJoinPool)
        //we'd like to do it using virtual thread
    }

    private static void demo2(){
        log.info("demo started");
        runAsyncWithVirtualThread();
        log.info("demo ended");
        Util.sleepSeconds(2);

        //it is now using virtual thread

        /*
        07:43:45.947 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- demo started
07:43:45.954 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- runAsync started
07:43:45.992 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- runAsync ended
07:43:45.992 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- demo ended
07:43:47.009 [virtual-22] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- runAsync completed
         */
    }

    //what if you don't care about what the method would return but would like
    // to know whether the task is done or not?
    private static void demo3(){
        log.info("demo started");
        runAsyncWithVirtualThread1().thenRun(()->log.info("it is done"));
        log.info("demo ended");
        Util.sleepSeconds(2);

        /*
            07:48:21.621 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- demo started
07:48:21.622 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- runAsync started
07:48:21.636 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- runAsync ended
07:48:21.637 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- demo ended
07:48:22.645 [virtual-22] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- runAsync completed
07:48:22.645 [virtual-22] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- it is done
         */
    }

    /*
    what if the task method couldn't complete the task?
    maybe it ran into some exception
    how would the other method know?
     */

    private static void demo4(){
        log.info("demo started");
        runAsyncWithVirtualThread2().thenRun(()->log.info("it is done"));
        log.info("demo ended");
        Util.sleepSeconds(2);

        // it won't print 'it is done'

        /*
            07:52:16.381 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- demo started
07:52:16.381 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- runAsync started
07:52:16.391 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- runAsync ended
07:52:16.391 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- demo ended
         */
    }

    private static void demo5(){
        log.info("demo started");
        runAsyncWithVirtualThread2().thenRun(()->log.info("it is done"))
                        .exceptionally(ex->{
                            log.error("Error: {}", ex.getMessage());
                            return null;
                        });
        log.info("demo ended");
        Util.sleepSeconds(2);

        /*
            07:54:26.343 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- demo started
07:54:26.351 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- runAsync started
07:54:26.361 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- runAsync ended
07:54:26.391 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- demo ended
07:54:27.407 [virtual-22] ERROR com.example.playgroundproject.completabale_future.sec08.Lec02RunAsync -- Error: java.lang.RuntimeException: oops!
         */
    }


}
