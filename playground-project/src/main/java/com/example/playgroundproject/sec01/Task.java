package com.example.playgroundproject.sec01;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class Task {
    public static void ioIntensiveTask(int i){
        try {
            log.info("STARTING I/O INTENSIVE TASK: {}",i);
            Thread.sleep(Duration.ofSeconds(10));
            log.info("ENDING I/O INTENSIVE TASK: {}",i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void ioIntensiveTaskWithInfo(int i){
        try {
            log.info("STARTING I/O INTENSIVE TASK: {} THREAD INFO; {}",i,Thread.currentThread());
            Thread.sleep(Duration.ofSeconds(10));
            log.info("ENDING I/O INTENSIVE TASK: {} THREAD INFO: {}",i,Thread.currentThread());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
