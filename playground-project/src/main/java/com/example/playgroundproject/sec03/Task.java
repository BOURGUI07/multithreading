package com.example.playgroundproject.sec03;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Task {
    public static int findFib(int n) {
        if (n <2) return n;
        return findFib(n-1) + findFib(n-2);
    }

    public static void cpuIntensive(int i){
        log.info("STARTING CPU TASK. THREAD INFO: {}", Thread.currentThread());
        Util.timer(()->findFib(i));
        log.info("ENDING CPU TASK. THREAD INFO: {}", Thread.currentThread());
    }
}
