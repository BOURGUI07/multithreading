package com.example.playgroundproject.virtual_threads.sec02;

import com.example.playgroundproject.Util;

public class StackTraceDemo {
    public static void main(String[] args) {
        demoVirtual();
    }

    private static void demo(Thread.Builder builder){
        for(int i=1;i<=20;i++){
            int j=i;
            builder.start(()->Task.execute(j));
        }
    }

    private static void demoPlatform(){
        demo(Thread.ofPlatform());

        /*
            08:17:49.653 [Thread-13] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 14
08:17:49.653 [Thread-11] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 12
08:17:49.653 [Thread-14] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 15
08:17:49.653 [Thread-15] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 16
08:17:49.653 [Thread-3] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 4
08:17:49.653 [Thread-1] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 2
08:17:49.653 [Thread-4] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 5
08:17:49.653 [Thread-9] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 10
08:17:49.653 [Thread-7] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 8
08:17:49.653 [Thread-10] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 11
08:17:49.653 [Thread-19] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 20
08:17:49.653 [Thread-0] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 1
08:17:49.653 [Thread-16] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 17
08:17:49.653 [Thread-17] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 18
08:17:49.653 [Thread-5] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 6
08:17:49.653 [Thread-12] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 13
08:17:49.653 [Thread-2] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 3
08:17:49.653 [Thread-6] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 7
08:17:49.653 [Thread-18] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 19
08:17:49.653 [Thread-8] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 9
08:17:50.591 [Thread-1] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 2
08:17:50.591 [Thread-12] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 13
08:17:50.591 [Thread-7] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 8
08:17:50.591 [Thread-9] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 10
08:17:50.591 [Thread-19] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 20
08:17:50.591 [Thread-8] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 9
08:17:50.591 [Thread-10] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 11
08:17:50.591 [Thread-17] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 18
08:17:50.591 [Thread-11] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 12
08:17:50.591 [Thread-4] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 5
08:17:50.591 [Thread-14] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 15
08:17:50.591 [Thread-15] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 16
08:17:50.591 [Thread-2] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 3
08:17:50.591 [Thread-3] ERROR com.example.playgroundproject.sec02.Task -- ERROR: java.lang.IllegalArgumentException: i can't be 4 for: 4
08:17:50.591 [Thread-6] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 7
08:17:50.591 [Thread-16] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 17
08:17:50.591 [Thread-5] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 6
08:17:50.591 [Thread-0] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 1
08:17:50.591 [Thread-13] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 14
08:17:50.592 [Thread-3] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 4
08:17:50.592 [Thread-18] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 19
         */
    }

    private static void demoVirtual(){
        demo(Thread.ofVirtual().name("VIRTUAL-",1));
        Util.sleepSeconds(2);

        /*
            08:19:28.317 [VIRTUAL-6] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 6
08:19:28.317 [VIRTUAL-3] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 3
08:19:28.333 [VIRTUAL-12] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 12
08:19:28.333 [VIRTUAL-15] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 15
08:19:28.333 [VIRTUAL-16] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 16
08:19:28.334 [VIRTUAL-17] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 17
08:19:28.334 [VIRTUAL-18] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 18
08:19:28.334 [VIRTUAL-20] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 20
08:19:28.317 [VIRTUAL-7] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 7
08:19:28.317 [VIRTUAL-1] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 1
08:19:28.331 [VIRTUAL-9] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 9
08:19:28.317 [VIRTUAL-5] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 5
08:19:28.331 [VIRTUAL-11] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 11
08:19:28.317 [VIRTUAL-8] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 8
08:19:28.331 [VIRTUAL-10] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 10
08:19:28.333 [VIRTUAL-14] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 14
08:19:28.333 [VIRTUAL-13] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 13
08:19:28.334 [VIRTUAL-19] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 19
08:19:28.317 [VIRTUAL-2] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 2
08:19:28.317 [VIRTUAL-4] INFO com.example.playgroundproject.sec02.Task -- STARTING TASK: 4
08:19:29.259 [VIRTUAL-17] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 17
08:19:29.259 [VIRTUAL-18] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 18
08:19:29.259 [VIRTUAL-16] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 16
08:19:29.259 [VIRTUAL-15] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 15
08:19:29.259 [VIRTUAL-12] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 12
08:19:29.259 [VIRTUAL-20] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 20
08:19:29.259 [VIRTUAL-1] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 1
08:19:29.260 [VIRTUAL-9] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 9
08:19:29.259 [VIRTUAL-6] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 6
08:19:29.260 [VIRTUAL-11] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 11
08:19:29.259 [VIRTUAL-3] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 3
08:19:29.259 [VIRTUAL-7] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 7
08:19:29.260 [VIRTUAL-5] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 5
08:19:29.260 [VIRTUAL-8] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 8
08:19:29.260 [VIRTUAL-10] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 10
08:19:29.260 [VIRTUAL-14] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 14
08:19:29.260 [VIRTUAL-13] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 13
08:19:29.261 [VIRTUAL-19] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 19
08:19:29.261 [VIRTUAL-2] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 2
08:19:29.261 [VIRTUAL-4] ERROR com.example.playgroundproject.sec02.Task -- ERROR: java.lang.IllegalArgumentException: i can't be 4 for: 4
08:19:29.261 [VIRTUAL-4] INFO com.example.playgroundproject.sec02.Task -- ENDING TASK: 4
         */
    }
}
