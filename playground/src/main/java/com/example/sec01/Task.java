package com.example.sec01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Task {
    private static final Logger  log= LoggerFactory.getLogger(Task.class);

    public static void ioIntensiveTask(int i){
        try {
            log.info("STARTING I/O INTENSIVE TASK: {}",i);
            Thread.sleep(Duration.ofSeconds(10));
            log.info("ENDING I/O INTENSIVE TASK: {}",i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
