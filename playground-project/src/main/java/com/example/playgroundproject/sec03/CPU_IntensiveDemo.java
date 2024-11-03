package com.example.playgroundproject.sec03;

import java.util.concurrent.CountDownLatch;

public class CPU_IntensiveDemo {
    public static void main(String[] args) {
        virtualDemo1();
    }

    private static void demo(Thread.Builder builder, int count){
        var latch = new CountDownLatch(1);
        for(int i = 1; i <= count ; i++){
            builder.start(()->{
                Task.cpuIntensive(45);
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //Each time you increase the number of threads, the cpu intensive task will take more duration to execute
    // if you have 8 cpus in your machine and you want to start 8 threads then each cpu will be assigned one thread
    // if you have 8 cpus, and you wanna start 16 threads, then each cpu will be assigned two threads.
    private static void platformDemo(){
        demo(Thread.ofPlatform(),Runtime.getRuntime().availableProcessors());
        /*
            08:44:31.417 [Thread-7] INFO com.example.playgroundproject.sec03.Task -- STARTING CPU TASK. THREAD INFO: Thread[#29,Thread-7,5,main]
08:44:31.417 [Thread-4] INFO com.example.playgroundproject.sec03.Task -- STARTING CPU TASK. THREAD INFO: Thread[#26,Thread-4,5,main]
08:44:31.417 [Thread-5] INFO com.example.playgroundproject.sec03.Task -- STARTING CPU TASK. THREAD INFO: Thread[#27,Thread-5,5,main]
08:44:31.417 [Thread-2] INFO com.example.playgroundproject.sec03.Task -- STARTING CPU TASK. THREAD INFO: Thread[#24,Thread-2,5,main]
08:44:31.417 [Thread-1] INFO com.example.playgroundproject.sec03.Task -- STARTING CPU TASK. THREAD INFO: Thread[#23,Thread-1,5,main]
08:44:31.417 [Thread-3] INFO com.example.playgroundproject.sec03.Task -- STARTING CPU TASK. THREAD INFO: Thread[#25,Thread-3,5,main]
08:44:31.417 [Thread-0] INFO com.example.playgroundproject.sec03.Task -- STARTING CPU TASK. THREAD INFO: Thread[#22,Thread-0,5,main]
08:44:31.417 [Thread-6] INFO com.example.playgroundproject.sec03.Task -- STARTING CPU TASK. THREAD INFO: Thread[#28,Thread-6,5,main]
Elapsed time: 13217
Elapsed time: 13215
08:44:44.663 [Thread-2] INFO com.example.playgroundproject.sec03.Task -- ENDING CPU TASK. THREAD INFO: Thread[#24,Thread-2,5,main]
08:44:44.663 [Thread-5] INFO com.example.playgroundproject.sec03.Task -- ENDING CPU TASK. THREAD INFO: Thread[#27,Thread-5,5,main]
Elapsed time: 13263
08:44:44.702 [Thread-1] INFO com.example.playgroundproject.sec03.Task -- ENDING CPU TASK. THREAD INFO: Thread[#23,Thread-1,5,main]
Elapsed time: 13273
08:44:44.713 [Thread-6] INFO com.example.playgroundproject.sec03.Task -- ENDING CPU TASK. THREAD INFO: Thread[#28,Thread-6,5,main]
Elapsed time: 13305
08:44:44.749 [Thread-7] INFO com.example.playgroundproject.sec03.Task -- ENDING CPU TASK. THREAD INFO: Thread[#29,Thread-7,5,main]
Elapsed time: 13346
08:44:44.786 [Thread-0] INFO com.example.playgroundproject.sec03.Task -- ENDING CPU TASK. THREAD INFO: Thread[#22,Thread-0,5,main]
Elapsed time: 13376
08:44:44.816 [Thread-3] INFO com.example.playgroundproject.sec03.Task -- ENDING CPU TASK. THREAD INFO: Thread[#25,Thread-3,5,main]
Elapsed time: 13398
08:44:44.837 [Thread-4] INFO com.example.playgroundproject.sec03.Task -- ENDING CPU TASK. THREAD INFO: Thread[#26,Thread-4,5,main]
         */
    }

    //even if you increase the number of started threads, each task gonna take the same duration
    private static void virtualDemo(){
        demo(Thread.ofVirtual().name("VIRTUAL-",1),2);


    }


    private static void virtualDemo1(){
        demo(Thread.ofVirtual(),8);


    }

    // if you started 8 platform threads doing intensive task, there will be 8 threads
    // if you started 16 platform threads doing intensive task, there will be 16 threads
    // if you start 16 virtual threads, only 8 will be created, as soon a worker finishes his task is gonna do the other task
}
