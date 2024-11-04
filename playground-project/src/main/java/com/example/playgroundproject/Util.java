package com.example.playgroundproject;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class Util {
    public static void sleepSeconds(long seconds) {
        try {
            Thread.sleep(Duration.ofSeconds(seconds));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sleepMillis(long millis) {
        try {
            Thread.sleep(Duration.ofMillis(millis));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void timer(Runnable runnable) {
        var start = System.currentTimeMillis();
        runnable.run();
        var end = System.currentTimeMillis();
        var elapsed = end - start;
        System.out.println("Elapsed time: " + elapsed);
    }

    public static void sleepWithTask(String task, long secs){
        try {
            Thread.sleep(Duration.ofSeconds(secs));
        } catch (InterruptedException e) {
            log.error("Cancelling task: {}", task);
        }
    }
}
