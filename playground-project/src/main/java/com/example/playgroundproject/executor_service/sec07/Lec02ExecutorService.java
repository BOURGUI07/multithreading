package com.example.playgroundproject.executor_service.sec07;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Lec02ExecutorService {
    public static void main(String[] args) {
        singleThreadSchedulerExecutor();
    }

    private static void task(int i){
        log.info("task started: {}. Thread info: {}", i, Thread.currentThread());
        Util.sleepSeconds(5);
        log.info("task ended: {}. Thread info: {}", i, Thread.currentThread());
    }

    private static void execute(ExecutorService service, int taskCount){
        try(service){
            for(int i=0; i<taskCount; i++){
                var j=i;
                service.submit(() -> task(j));
            }
            log.info("task submitted");
        }
    }

    // if you wanna execute tasks sequentially one by one
    private static void singleThreadExecutor(){
        execute(Executors.newSingleThreadExecutor(), 3);

        /*/
            16:49:15.905 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 0. Thread info: Thread[#22,pool-1-thread-1,5,main]
16:49:15.905 [main] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task submitted
16:49:20.914 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 0. Thread info: Thread[#22,pool-1-thread-1,5,main]
16:49:20.916 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 1. Thread info: Thread[#22,pool-1-thread-1,5,main]
16:49:25.931 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 1. Thread info: Thread[#22,pool-1-thread-1,5,main]
16:49:25.931 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 2. Thread info: Thread[#22,pool-1-thread-1,5,main]
16:49:30.933 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 2. Thread info: Thread[#22,pool-1-thread-1,5,main]

         */
    }

    private static void fixedThreadPool(){
        execute(Executors.newFixedThreadPool(5), 20);
        /*
            16:53:52.328 [pool-1-thread-4] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 3. Thread info: Thread[#25,pool-1-thread-4,5,main]
16:53:52.328 [pool-1-thread-3] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 2. Thread info: Thread[#24,pool-1-thread-3,5,main]
16:53:52.328 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 0. Thread info: Thread[#22,pool-1-thread-1,5,main]
16:53:52.328 [main] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task submitted
16:53:52.328 [pool-1-thread-2] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 1. Thread info: Thread[#23,pool-1-thread-2,5,main]
16:53:52.328 [pool-1-thread-5] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 4. Thread info: Thread[#26,pool-1-thread-5,5,main]
16:53:57.350 [pool-1-thread-3] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 2. Thread info: Thread[#24,pool-1-thread-3,5,main]
16:53:57.350 [pool-1-thread-2] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 1. Thread info: Thread[#23,pool-1-thread-2,5,main]
16:53:57.350 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 0. Thread info: Thread[#22,pool-1-thread-1,5,main]
16:53:57.350 [pool-1-thread-5] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 4. Thread info: Thread[#26,pool-1-thread-5,5,main]
16:53:57.350 [pool-1-thread-4] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 3. Thread info: Thread[#25,pool-1-thread-4,5,main]
16:53:57.350 [pool-1-thread-2] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 5. Thread info: Thread[#23,pool-1-thread-2,5,main]
16:53:57.350 [pool-1-thread-5] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 8. Thread info: Thread[#26,pool-1-thread-5,5,main]
16:53:57.350 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 9. Thread info: Thread[#22,pool-1-thread-1,5,main]
16:53:57.350 [pool-1-thread-3] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 7. Thread info: Thread[#24,pool-1-thread-3,5,main]
16:53:57.350 [pool-1-thread-4] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 6. Thread info: Thread[#25,pool-1-thread-4,5,main]
16:54:02.362 [pool-1-thread-4] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 6. Thread info: Thread[#25,pool-1-thread-4,5,main]
16:54:02.362 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 9. Thread info: Thread[#22,pool-1-thread-1,5,main]
16:54:02.362 [pool-1-thread-5] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 8. Thread info: Thread[#26,pool-1-thread-5,5,main]
16:54:02.362 [pool-1-thread-3] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 7. Thread info: Thread[#24,pool-1-thread-3,5,main]
16:54:02.362 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 11. Thread info: Thread[#22,pool-1-thread-1,5,main]
16:54:02.362 [pool-1-thread-2] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 5. Thread info: Thread[#23,pool-1-thread-2,5,main]
16:54:02.362 [pool-1-thread-4] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 10. Thread info: Thread[#25,pool-1-thread-4,5,main]
16:54:02.362 [pool-1-thread-5] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 12. Thread info: Thread[#26,pool-1-thread-5,5,main]
16:54:02.362 [pool-1-thread-2] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 14. Thread info: Thread[#23,pool-1-thread-2,5,main]
16:54:02.362 [pool-1-thread-3] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 13. Thread info: Thread[#24,pool-1-thread-3,5,main]
16:54:07.365 [pool-1-thread-4] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 10. Thread info: Thread[#25,pool-1-thread-4,5,main]
16:54:07.365 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 11. Thread info: Thread[#22,pool-1-thread-1,5,main]
16:54:07.365 [pool-1-thread-4] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 15. Thread info: Thread[#25,pool-1-thread-4,5,main]
16:54:07.365 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 16. Thread info: Thread[#22,pool-1-thread-1,5,main]
16:54:07.366 [pool-1-thread-2] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 14. Thread info: Thread[#23,pool-1-thread-2,5,main]
16:54:07.366 [pool-1-thread-3] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 13. Thread info: Thread[#24,pool-1-thread-3,5,main]
16:54:07.366 [pool-1-thread-5] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 12. Thread info: Thread[#26,pool-1-thread-5,5,main]
16:54:07.366 [pool-1-thread-3] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 18. Thread info: Thread[#24,pool-1-thread-3,5,main]
16:54:07.366 [pool-1-thread-2] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 17. Thread info: Thread[#23,pool-1-thread-2,5,main]
16:54:07.366 [pool-1-thread-5] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 19. Thread info: Thread[#26,pool-1-thread-5,5,main]
16:54:12.376 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 16. Thread info: Thread[#22,pool-1-thread-1,5,main]
16:54:12.376 [pool-1-thread-2] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 17. Thread info: Thread[#23,pool-1-thread-2,5,main]
16:54:12.376 [pool-1-thread-5] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 19. Thread info: Thread[#26,pool-1-thread-5,5,main]
16:54:12.376 [pool-1-thread-3] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 18. Thread info: Thread[#24,pool-1-thread-3,5,main]
16:54:12.376 [pool-1-thread-4] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 15. Thread info: Thread[#25,pool-1-thread-4,5,main]
         */
    }


    //it will start with 0 threads, and will create as many threads as tasks
    private static void cachedThreadPool(){
        execute(Executors.newCachedThreadPool(), 10);
        // will create 10 threads

        /*
            16:56:02.868 [pool-1-thread-6] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 5. Thread info: Thread[#27,pool-1-thread-6,5,main]
16:56:02.868 [main] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task submitted
16:56:02.868 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 0. Thread info: Thread[#22,pool-1-thread-1,5,main]
16:56:02.868 [pool-1-thread-5] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 4. Thread info: Thread[#26,pool-1-thread-5,5,main]
16:56:02.868 [pool-1-thread-10] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 9. Thread info: Thread[#31,pool-1-thread-10,5,main]
16:56:02.868 [pool-1-thread-4] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 3. Thread info: Thread[#25,pool-1-thread-4,5,main]
16:56:02.868 [pool-1-thread-8] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 7. Thread info: Thread[#29,pool-1-thread-8,5,main]
16:56:02.868 [pool-1-thread-2] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 1. Thread info: Thread[#23,pool-1-thread-2,5,main]
16:56:02.868 [pool-1-thread-7] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 6. Thread info: Thread[#28,pool-1-thread-7,5,main]
16:56:02.868 [pool-1-thread-3] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 2. Thread info: Thread[#24,pool-1-thread-3,5,main]
16:56:02.868 [pool-1-thread-9] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 8. Thread info: Thread[#30,pool-1-thread-9,5,main]
16:56:07.877 [pool-1-thread-8] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 7. Thread info: Thread[#29,pool-1-thread-8,5,main]
16:56:07.877 [pool-1-thread-4] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 3. Thread info: Thread[#25,pool-1-thread-4,5,main]
16:56:07.884 [pool-1-thread-6] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 5. Thread info: Thread[#27,pool-1-thread-6,5,main]
16:56:07.884 [pool-1-thread-7] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 6. Thread info: Thread[#28,pool-1-thread-7,5,main]
16:56:07.884 [pool-1-thread-10] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 9. Thread info: Thread[#31,pool-1-thread-10,5,main]
16:56:07.884 [pool-1-thread-2] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 1. Thread info: Thread[#23,pool-1-thread-2,5,main]
16:56:07.884 [pool-1-thread-5] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 4. Thread info: Thread[#26,pool-1-thread-5,5,main]
16:56:07.884 [pool-1-thread-3] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 2. Thread info: Thread[#24,pool-1-thread-3,5,main]
16:56:07.884 [pool-1-thread-9] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 8. Thread info: Thread[#30,pool-1-thread-9,5,main]
16:56:07.884 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 0. Thread info: Thread[#22,pool-1-thread-1,5,main]
         */
    }

    //will create as many virtual threads as tasks
    private static void virtualThreadPerTaskExecutor(){
        execute(Executors.newVirtualThreadPerTaskExecutor(), 10);
        // will create 10 virtual threads

        /*
            16:58:50.721 [main] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task submitted
16:58:50.721 [virtual-27] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 4. Thread info: VirtualThread[#27]/runnable@ForkJoinPool-1-worker-5
16:58:50.721 [virtual-25] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 2. Thread info: VirtualThread[#25]/runnable@ForkJoinPool-1-worker-3
16:58:50.721 [virtual-29] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 6. Thread info: VirtualThread[#29]/runnable@ForkJoinPool-1-worker-7
16:58:50.721 [virtual-22] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 0. Thread info: VirtualThread[#22]/runnable@ForkJoinPool-1-worker-1
16:58:50.721 [virtual-26] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 3. Thread info: VirtualThread[#26]/runnable@ForkJoinPool-1-worker-4
16:58:50.721 [virtual-30] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 7. Thread info: VirtualThread[#30]/runnable@ForkJoinPool-1-worker-8
16:58:50.727 [virtual-31] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 8. Thread info: VirtualThread[#31]/runnable@ForkJoinPool-1-worker-6
16:58:50.727 [virtual-32] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 9. Thread info: VirtualThread[#32]/runnable@ForkJoinPool-1-worker-2
16:58:50.721 [virtual-24] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 1. Thread info: VirtualThread[#24]/runnable@ForkJoinPool-1-worker-2
16:58:50.721 [virtual-28] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task started: 5. Thread info: VirtualThread[#28]/runnable@ForkJoinPool-1-worker-6
16:58:55.731 [virtual-29] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 6. Thread info: VirtualThread[#29]/runnable@ForkJoinPool-1-worker-1
16:58:55.731 [virtual-27] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 4. Thread info: VirtualThread[#27]/runnable@ForkJoinPool-1-worker-7
16:58:55.731 [virtual-25] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 2. Thread info: VirtualThread[#25]/runnable@ForkJoinPool-1-worker-5
16:58:55.731 [virtual-26] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 3. Thread info: VirtualThread[#26]/runnable@ForkJoinPool-1-worker-4
16:58:55.731 [virtual-32] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 9. Thread info: VirtualThread[#32]/runnable@ForkJoinPool-1-worker-3
16:58:55.732 [virtual-28] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 5. Thread info: VirtualThread[#28]/runnable@ForkJoinPool-1-worker-3
16:58:55.731 [virtual-31] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 8. Thread info: VirtualThread[#31]/runnable@ForkJoinPool-1-worker-6
16:58:55.731 [virtual-22] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 0. Thread info: VirtualThread[#22]/runnable@ForkJoinPool-1-worker-8
16:58:55.732 [virtual-30] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 7. Thread info: VirtualThread[#30]/runnable@ForkJoinPool-1-worker-6
16:58:55.732 [virtual-24] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- task ended: 1. Thread info: VirtualThread[#24]/runnable@ForkJoinPool-1-worker-2
         */

    }

    private static void singleThreadSchedulerExecutor(){
        try(var service = Executors.newSingleThreadScheduledExecutor()){
            service.scheduleWithFixedDelay(() -> {
                log.info("executing task");
            },0,1, TimeUnit.SECONDS); // once every second
            Util.sleepSeconds(5); //block for 5 secs
        }

        /*
            17:05:51.301 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- executing task
17:05:52.320 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- executing task
17:05:53.327 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- executing task
17:05:54.336 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- executing task
17:05:55.343 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec02ExecutorService -- executing task
         */
    }


}
