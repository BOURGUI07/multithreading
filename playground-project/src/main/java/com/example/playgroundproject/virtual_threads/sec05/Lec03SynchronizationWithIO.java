package com.example.playgroundproject.virtual_threads.sec05;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Lec03SynchronizationWithIO {
    private static final List<Integer> list = new ArrayList<>();
    public static void main(String[] args) {
        demo1();
    }

    public static synchronized void ioTask(){
        list.add(1);
        Util.sleepSeconds(10);
    }

    private static void demo(Thread.Builder builder){
        for (int i = 0; i <50; i++) {
            builder.start(()->{
                log.info("TASK STARTED: {}", Thread.currentThread());
                ioTask();
                log.info("TASK ENDED: {}", Thread.currentThread());
            });
        }
        Util.sleepSeconds(15);
    }


    //it will start 50 threads immediately
    private static void demo1(){
        demo(Thread.ofPlatform());
    }

    //it will start the number of cpu available in machine as number of threads
    //only after a thread finished his task, another thread will be created
    // if you want the threads to be started right away, remove the synchronized keyword
    private static void demo2(){
        demo(Thread.ofVirtual());
    }
}
