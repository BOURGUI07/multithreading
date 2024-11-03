package com.example.playgroundproject.virtual_threads.sec02;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Task {

    public static void execute(int i){
        log.info("STARTING TASK: {}",i);
        try{
            method1(i);
        }catch(Exception e){
            log.error("ERROR: {} for: {}",e.getMessage(),i);
        }
        log.info("ENDING TASK: {}",i);
    }

    public static void method1(int i){
        Util.sleepMillis(300);
        try{
            method2(i);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void method2(int i){
        Util.sleepMillis(100);
        method3(i);
    }

    public static void method3(int i){
        Util.sleepMillis(500);
        if(i==4) throw new IllegalArgumentException("i can't be 4");
    }
}
