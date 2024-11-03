package com.example.playgroundproject.virtual_threads.sec04;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CooperativeSchedulingDemo {


    public static void main(String[] args) {
        demo1();
    }

    private static void demo(int threadNumber){
        log.info("Thread-{} STARTED", threadNumber);
        for(int i=0; i<10; i++){
            log.info("Thread-{} is printing {} thread {}", threadNumber, i, Thread.currentThread());
        }
        log.info("Thread-{} ENDED", threadNumber);
    }

    private static void demo1(){
        var builder = Thread.ofVirtual();
        var thread = builder.unstarted(()->demo(1));
        var thread1 = builder.unstarted(()->demo(2));
        thread.start();
        thread1.start();
        Util.sleepSeconds(2);



        /*
            09:51:02.056 [virtual-23] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-2 STARTED
09:51:02.056 [virtual-22] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-1 STARTED
09:51:02.066 [virtual-22] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-1 is printing 0 thread VirtualThread[#22]/runnable@ForkJoinPool-1-worker-3
09:51:02.066 [virtual-23] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-2 is printing 0 thread VirtualThread[#23]/runnable@ForkJoinPool-1-worker-2
09:51:02.067 [virtual-22] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-1 is printing 1 thread VirtualThread[#22]/runnable@ForkJoinPool-1-worker-3
09:51:02.067 [virtual-23] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-2 is printing 1 thread VirtualThread[#23]/runnable@ForkJoinPool-1-worker-2
09:51:02.067 [virtual-22] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-1 is printing 2 thread VirtualThread[#22]/runnable@ForkJoinPool-1-worker-3
09:51:02.067 [virtual-23] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-2 is printing 2 thread VirtualThread[#23]/runnable@ForkJoinPool-1-worker-2
09:51:02.067 [virtual-22] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-1 is printing 3 thread VirtualThread[#22]/runnable@ForkJoinPool-1-worker-3
09:51:02.067 [virtual-23] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-2 is printing 3 thread VirtualThread[#23]/runnable@ForkJoinPool-1-worker-2
09:51:02.067 [virtual-22] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-1 is printing 4 thread VirtualThread[#22]/runnable@ForkJoinPool-1-worker-3
09:51:02.067 [virtual-23] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-2 is printing 4 thread VirtualThread[#23]/runnable@ForkJoinPool-1-worker-2
09:51:02.067 [virtual-22] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-1 is printing 5 thread VirtualThread[#22]/runnable@ForkJoinPool-1-worker-3
09:51:02.067 [virtual-23] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-2 is printing 5 thread VirtualThread[#23]/runnable@ForkJoinPool-1-worker-2
09:51:02.067 [virtual-22] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-1 is printing 6 thread VirtualThread[#22]/runnable@ForkJoinPool-1-worker-3
09:51:02.067 [virtual-23] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-2 is printing 6 thread VirtualThread[#23]/runnable@ForkJoinPool-1-worker-2
09:51:02.067 [virtual-22] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-1 is printing 7 thread VirtualThread[#22]/runnable@ForkJoinPool-1-worker-3
09:51:02.067 [virtual-23] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-2 is printing 7 thread VirtualThread[#23]/runnable@ForkJoinPool-1-worker-2
09:51:02.067 [virtual-22] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-1 is printing 8 thread VirtualThread[#22]/runnable@ForkJoinPool-1-worker-3
09:51:02.067 [virtual-23] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-2 is printing 8 thread VirtualThread[#23]/runnable@ForkJoinPool-1-worker-2
09:51:02.067 [virtual-22] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-1 is printing 9 thread VirtualThread[#22]/runnable@ForkJoinPool-1-worker-3
09:51:02.068 [virtual-23] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-2 is printing 9 thread VirtualThread[#23]/runnable@ForkJoinPool-1-worker-2
09:51:02.068 [virtual-22] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-1 ENDED
09:51:02.068 [virtual-23] INFO com.example.playgroundproject.sec04.CooperativeSchedulingDemo -- Thread-2 ENDED
         */
    }

    private static void demo2(){
        /*
            static {
        System.setProperty("jdk.VirtualThreadScheduler.parallelism", "1");
        System.getProperty("jdk.VirtualThreadScheduler.maxPoolSize","1");
    }
         */
        //now after modifying the pool size
        //thread one will start and then only after completing its tasks, the thread  2 will start
        // if you them to execute their tasks one by one between both thread add Thread.yield() after the log.info("Thread-{} is printing {} thread {}", threadNumber, i, Thread.currentThread());
        var builder = Thread.ofVirtual();
        var thread = builder.unstarted(()->demo(1));
        var thread1 = builder.unstarted(()->demo(2));
        thread.start();
        thread1.start();
        Util.sleepSeconds(2);


    }
}
