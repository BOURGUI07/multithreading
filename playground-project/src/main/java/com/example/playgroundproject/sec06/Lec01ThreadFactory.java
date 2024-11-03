package com.example.playgroundproject.sec06;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;

@Slf4j
public class Lec01ThreadFactory {
    public static void main(String[] args) {
        demoVirtual();
    }

    private static void demo(ThreadFactory factory) {
        for (int i = 0; i <3; i++) {
            var thread = factory.newThread(() ->{
                log.info("TASK STARTED: {}", Thread.currentThread());
                var childThread = factory.newThread(() ->{
                    log.info("CHILD TASK STARTED: {}", Thread.currentThread());
                    Util.sleepSeconds(2);
                    log.info("CHILD TASK ENDED: {}", Thread.currentThread());
                });
                childThread.start();
                log.info("TASK ENDED: {}", Thread.currentThread());
            });
            thread.start();
        }
        Util.sleepSeconds(15);
    }

    private static void demoVirtual() {
        demo(Thread.ofVirtual().name("VIRTUAL-",1).factory());
    }
}
