package com.example.playgroundproject.sec05;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Lec02Synchronization {
    private static final List<Integer> list = new ArrayList<>();
    public static void main(String[] args) {
        demo1();
    }

    public static synchronized void inMemoryTask(){
        list.add(1);
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

    //after adding synchronized keyword in add() method now

    //the list size is always 10000
    private static void demo1(){
        demo(Thread.ofPlatform());
    }

    //the list size is always  10000
    private static void demo2(){
        demo(Thread.ofVirtual());
    }
}
