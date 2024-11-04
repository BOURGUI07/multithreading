package com.example.playgroundproject.completabale_future.sec08;


import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class Lec01SimpleCompletableFuture {
    public static void main(String[] args) {
        demo3();
    }

    private static CompletableFuture<String> fastTask() {
        log.info("fastTask started");
        var cf = new CompletableFuture<String>();
        cf.complete("Hello World");
        log.info("fastTask ended");
        return cf;
    }

    private static CompletableFuture<String> slowTask() {
        log.info("slowTask started");
        var cf = new CompletableFuture<String>();
        Thread.ofVirtual().start(()->{
            Util.sleepSeconds(1);
            cf.complete("Hello World");
        });
        log.info("slowTask ended");
        return cf;
    }

    private static void demo(){
        log.info("demo started");
        var cf = fastTask();
        log.info("value: {}", cf.join());//it's blocking, don't proceed further until completable future completes
        log.info("demo ended");

        /*
            07:23:05.821 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec09SimpleCompletableFuture -- demo started
07:23:05.824 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec09SimpleCompletableFuture -- fastTask started
07:23:05.831 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec09SimpleCompletableFuture -- fastTask ended
07:23:05.832 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec09SimpleCompletableFuture -- value: Hello World
07:23:05.834 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec09SimpleCompletableFuture -- demo ended
         */
    }


    private static void demo2(){
        log.info("demo started");
        var cf = slowTask();
        log.info("value: {}", cf.join());//it's blocking, don't proceed further until completable future completes
        log.info("demo ended");

        /*
            07:28:19.240 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec09SimpleCompletableFuture -- demo started
07:28:19.245 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec09SimpleCompletableFuture -- slowTask started
07:28:19.291 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec09SimpleCompletableFuture -- slowTask ended
07:28:20.330 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec09SimpleCompletableFuture -- value: Hello World
07:28:20.335 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec09SimpleCompletableFuture -- demo ended
         */
    }

    private static void demo3(){
        log.info("demo started");
        var cf = slowTask();
        cf.thenAccept(value->log.info("value: {}",value));
        log.info("demo ended");
        //it exists immediately, because virtual threads are daemon in default
        //now it's no blocking, to get the value, I've to block the main thread
        Util.sleepSeconds(2);

        /*
            07:31:24.801 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec09SimpleCompletableFuture -- demo started
07:31:24.811 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec09SimpleCompletableFuture -- slowTask started
07:31:24.815 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec09SimpleCompletableFuture -- slowTask ended
07:31:24.820 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec09SimpleCompletableFuture -- demo ended
07:31:25.838 [virtual-22] INFO com.example.playgroundproject.completabale_future.sec08.Lec09SimpleCompletableFuture -- value: Hello World
         */

        //see that the main thread didn't have to wait for the value to come
        // it printed demo ended even before waiting for the value
        // that's asynchronous
    }



}
