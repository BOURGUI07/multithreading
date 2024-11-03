package com.example.playgroundproject.sec01;

import java.util.concurrent.CountDownLatch;

public class InboundOutboundTaskDemo {
    private static final int MAX_PLATFORM = 10;
    private static final int MAX_VIRTUAL = 10;

    public static void main(String[] args) throws InterruptedException {
        virtualThread4();
    }

    //you can't create 50000 threads with platform threads
    private static void platformThread1(){
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j=i;
            Thread thread = new Thread(() ->Task.ioIntensiveTask(j));
            thread.start();
        }

        /*
            07:05:21.095 [Thread-1] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 1
07:05:21.097 [Thread-3] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 3
07:05:21.096 [Thread-8] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 8
07:05:21.095 [Thread-5] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 5
07:05:21.095 [Thread-9] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 9
07:05:21.097 [Thread-4] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 4
07:05:21.097 [Thread-2] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 2
07:05:21.097 [Thread-6] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 6
07:05:21.097 [Thread-7] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 7
07:05:21.097 [Thread-0] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 0
07:05:31.114 [Thread-8] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 8
07:05:31.114 [Thread-1] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 1
07:05:31.114 [Thread-5] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 5
07:05:31.114 [Thread-4] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 4
07:05:31.114 [Thread-0] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 0
07:05:31.114 [Thread-7] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 7
07:05:31.114 [Thread-9] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 9
07:05:31.114 [Thread-6] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 6
07:05:31.114 [Thread-2] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 2
07:05:31.127 [Thread-3] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 3
         */

    }

    private static void platformThread2() {
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = Thread.ofPlatform().unstarted(() -> Task.ioIntensiveTask(j));
            thread.start();
        }

        // the same result as above
    }

    private static void platformThread3() {
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = Thread.ofPlatform().start(() -> Task.ioIntensiveTask(j));
        }

        // the same result as above
    }

    private static void platformThread4() {
        var builder = Thread.ofPlatform().name("youness",1);
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = builder.unstarted(() -> Task.ioIntensiveTask(j));
            thread.start();
        }

        // the same result as above
        // the names of threads will be youness-1 youness-2 youness-3........
    }

    private static void platformThread5() {
        var builder = Thread.ofPlatform().daemon().name("youness",1);
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = builder.unstarted(() -> Task.ioIntensiveTask(j));
            thread.start();
        }

        // in the background will start 10 threads and will exit immediately
        // it won't wait for the daemon threads to complete their tasks
        // if you want it to wait see next method

    }

    private static void platformThread6() throws InterruptedException {
        var latch = new CountDownLatch(MAX_PLATFORM); //it won't exit until 10 threads do their tasks
        var builder = Thread.ofPlatform().daemon().name("youness",1);
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = builder.unstarted(() ->{
                Task.ioIntensiveTask(j);
                latch.countDown(); //every thread has to do the count down
            });
            thread.start();
        }
        latch.await(); //wait for tasks to do their jobs

        /*
            07:26:20.079 [youness3] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 2
07:26:20.079 [youness7] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 6
07:26:20.079 [youness8] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 7
07:26:20.079 [youness6] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 5
07:26:20.079 [youness10] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 9
07:26:20.079 [youness2] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 1
07:26:20.079 [youness5] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 4
07:26:20.079 [youness4] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 3
07:26:20.079 [youness1] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 0
07:26:20.079 [youness9] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 8
07:26:30.097 [youness2] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 1
07:26:30.097 [youness3] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 2
07:26:30.097 [youness8] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 7
07:26:30.097 [youness5] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 4
07:26:30.097 [youness4] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 3
07:26:30.108 [youness9] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 8
07:26:30.108 [youness7] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 6
07:26:30.108 [youness1] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 0
07:26:30.108 [youness10] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 9
07:26:30.108 [youness6] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 5
         */

    }

    private static void virtualThread1() {
        var builder = Thread.ofVirtual();
        for (int i = 0; i < MAX_VIRTUAL; i++) {
            int j = i;
            Thread thread = builder.unstarted(() -> Task.ioIntensiveTask(j));
            thread.start();
        }

        //if you run this, it will exit immediately
        // because virtual threads are daemon threads in default
    }

    //with virtual threads you can create as many threads as you want!
    private static void virtualThread2() throws InterruptedException {
        var latch = new CountDownLatch(MAX_VIRTUAL);
        var builder = Thread.ofVirtual();
        for (int i = 0; i < MAX_VIRTUAL; i++) {
            int j = i;
            Thread thread = builder.unstarted(() ->{
                Task.ioIntensiveTask(j);
                latch.countDown();
            });
            thread.start();
        }
        latch.await();

        // now the output basically like the above methods
        //the difference is that virtual threads don't have any default name!
    }

    private static void platformThread7(){
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j=i;
            Thread thread = new Thread(() ->Task.ioIntensiveTaskWithInfo(j));
            thread.start();
        }

        /*
            07:52:36.705 [Thread-9] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 9 THREAD INFO; Thread[#31,Thread-9,5,main]
07:52:36.705 [Thread-7] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 7 THREAD INFO; Thread[#29,Thread-7,5,main]
07:52:36.705 [Thread-3] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 3 THREAD INFO; Thread[#25,Thread-3,5,main]
07:52:36.705 [Thread-5] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 5 THREAD INFO; Thread[#27,Thread-5,5,main]
07:52:36.705 [Thread-4] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 4 THREAD INFO; Thread[#26,Thread-4,5,main]
07:52:36.705 [Thread-2] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 2 THREAD INFO; Thread[#24,Thread-2,5,main]
07:52:36.705 [Thread-6] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 6 THREAD INFO; Thread[#28,Thread-6,5,main]
07:52:36.705 [Thread-1] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 1 THREAD INFO; Thread[#23,Thread-1,5,main]
07:52:36.705 [Thread-8] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 8 THREAD INFO; Thread[#30,Thread-8,5,main]
07:52:36.705 [Thread-0] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 0 THREAD INFO; Thread[#22,Thread-0,5,main]
07:52:46.725 [Thread-5] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 5 THREAD INFO: Thread[#27,Thread-5,5,main]
07:52:46.725 [Thread-1] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 1 THREAD INFO: Thread[#23,Thread-1,5,main]
07:52:46.725 [Thread-4] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 4 THREAD INFO: Thread[#26,Thread-4,5,main]
07:52:46.726 [Thread-6] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 6 THREAD INFO: Thread[#28,Thread-6,5,main]
07:52:46.725 [Thread-8] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 8 THREAD INFO: Thread[#30,Thread-8,5,main]
07:52:46.725 [Thread-9] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 9 THREAD INFO: Thread[#31,Thread-9,5,main]
07:52:46.726 [Thread-2] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 2 THREAD INFO: Thread[#24,Thread-2,5,main]
07:52:46.725 [Thread-0] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 0 THREAD INFO: Thread[#22,Thread-0,5,main]
07:52:46.725 [Thread-3] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 3 THREAD INFO: Thread[#25,Thread-3,5,main]
07:52:46.725 [Thread-7] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 7 THREAD INFO: Thread[#29,Thread-7,5,main]
         */
    }

    private static void virtualThread4() throws InterruptedException {
        var latch = new CountDownLatch(MAX_VIRTUAL);
        var builder = Thread.ofVirtual().name("YOUNESS-",1);
        for (int i = 0; i < MAX_VIRTUAL; i++) {
            int j = i;
            Thread thread = builder.unstarted(() ->{
                Task.ioIntensiveTaskWithInfo(j);
                latch.countDown();
            });
            thread.start();
        }
        latch.await();

        /*
            07:56:07.078 [YOUNESS-8] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 7 THREAD INFO; VirtualThread[#30,YOUNESS-8]/runnable@ForkJoinPool-1-worker-8
07:56:07.078 [YOUNESS-1] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 0 THREAD INFO; VirtualThread[#22,YOUNESS-1]/runnable@ForkJoinPool-1-worker-1
07:56:07.078 [YOUNESS-2] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 1 THREAD INFO; VirtualThread[#24,YOUNESS-2]/runnable@ForkJoinPool-1-worker-2
07:56:07.090 [YOUNESS-9] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 8 THREAD INFO; VirtualThread[#31,YOUNESS-9]/runnable@ForkJoinPool-1-worker-7
07:56:07.078 [YOUNESS-6] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 5 THREAD INFO; VirtualThread[#28,YOUNESS-6]/runnable@ForkJoinPool-1-worker-6
07:56:07.078 [YOUNESS-5] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 4 THREAD INFO; VirtualThread[#27,YOUNESS-5]/runnable@ForkJoinPool-1-worker-5
07:56:07.078 [YOUNESS-3] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 2 THREAD INFO; VirtualThread[#25,YOUNESS-3]/runnable@ForkJoinPool-1-worker-3
07:56:07.094 [YOUNESS-10] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 9 THREAD INFO; VirtualThread[#32,YOUNESS-10]/runnable@ForkJoinPool-1-worker-4
07:56:07.078 [YOUNESS-4] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 3 THREAD INFO; VirtualThread[#26,YOUNESS-4]/runnable@ForkJoinPool-1-worker-4
07:56:07.078 [YOUNESS-7] INFO com.example.playgroundproject.sec01.Task -- STARTING I/O INTENSIVE TASK: 6 THREAD INFO; VirtualThread[#29,YOUNESS-7]/runnable@ForkJoinPool-1-worker-7
07:56:17.099 [YOUNESS-2] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 1 THREAD INFO: VirtualThread[#24,YOUNESS-2]/runnable@ForkJoinPool-1-worker-2
07:56:17.100 [YOUNESS-10] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 9 THREAD INFO: VirtualThread[#32,YOUNESS-10]/runnable@ForkJoinPool-1-worker-4
07:56:17.100 [YOUNESS-6] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 5 THREAD INFO: VirtualThread[#28,YOUNESS-6]/runnable@ForkJoinPool-1-worker-7
07:56:17.100 [YOUNESS-3] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 2 THREAD INFO: VirtualThread[#25,YOUNESS-3]/runnable@ForkJoinPool-1-worker-5
07:56:17.100 [YOUNESS-5] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 4 THREAD INFO: VirtualThread[#27,YOUNESS-5]/runnable@ForkJoinPool-1-worker-3
07:56:17.100 [YOUNESS-9] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 8 THREAD INFO: VirtualThread[#31,YOUNESS-9]/runnable@ForkJoinPool-1-worker-1
07:56:17.101 [YOUNESS-1] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 0 THREAD INFO: VirtualThread[#22,YOUNESS-1]/runnable@ForkJoinPool-1-worker-6
07:56:17.101 [YOUNESS-8] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 7 THREAD INFO: VirtualThread[#30,YOUNESS-8]/runnable@ForkJoinPool-1-worker-1
07:56:17.101 [YOUNESS-4] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 3 THREAD INFO: VirtualThread[#26,YOUNESS-4]/runnable@ForkJoinPool-1-worker-4
07:56:17.102 [YOUNESS-7] INFO com.example.playgroundproject.sec01.Task -- ENDING I/O INTENSIVE TASK: 6 THREAD INFO: VirtualThread[#29,YOUNESS-7]/runnable@ForkJoinPool-1-worker-3
         */
    }



}
