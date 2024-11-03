package com.example.playgroundproject.executor_service.sec07;

import com.example.playgroundproject.executor_service.sec07.externalService.Client;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Lec05ConcurrencyLimit {
    public static void main(String[] args) {
        fixedVirtualThreadPool();
    }

    public static void printProduct(int id){
        log.info("product-{} is: {}", id, Client.getProduct(id));
    }

    private static void execute(ExecutorService service, int count){
        try(service){
            for(int i=1; i<=count; i++){
                var j = i;
                service.execute(() -> printProduct(j));
            }
            log.info("submitted");
        }
    }


    private static void cachedThreadPool(){
        execute(Executors.newCachedThreadPool(),10);
        //if an external service makes a contract that says you're only allowed to make 3 concurrent requests per call
        // then you can't use cachedThreadPool. because cachedThreadPool will create as many threads as number of tasks
        // then you have to use fixedThreadPool, see next method

        /*
            18:50:17.584 [pool-1-thread-3] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/3
18:50:17.584 [pool-1-thread-7] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/7
18:50:17.584 [pool-1-thread-2] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/2
18:50:17.584 [pool-1-thread-6] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/6
18:50:17.584 [pool-1-thread-9] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/9
18:50:17.584 [main] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- submitted
18:50:17.584 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/1
18:50:17.584 [pool-1-thread-10] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/10
18:50:17.584 [pool-1-thread-4] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/4
18:50:17.584 [pool-1-thread-5] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/5
18:50:17.584 [pool-1-thread-8] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/8
18:50:18.631 [pool-1-thread-7] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-7 is: Fantastic Iron Knife
18:50:18.635 [pool-1-thread-2] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-2 is: Incredible Granite Bottle
18:50:18.634 [pool-1-thread-6] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-6 is: Lightweight Linen Lamp
18:50:18.634 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-1 is: Mediocre Linen Bench
18:50:18.634 [pool-1-thread-5] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-5 is: Gorgeous Granite Lamp
18:50:18.635 [pool-1-thread-9] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-9 is: Small Rubber Chair
18:50:18.631 [pool-1-thread-8] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-8 is: Enormous Silk Car
18:50:18.634 [pool-1-thread-3] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-3 is: Durable Aluminum Hat
18:50:18.631 [pool-1-thread-10] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-10 is: Synergistic Granite Shirt
18:50:18.631 [pool-1-thread-4] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-4 is: Sleek Silk Bag

         */

    }

    private static void fixedThreadPool(){
        execute(Executors.newFixedThreadPool(3),10);
        // make only 3 threads
        // only 3 calls will be made, only after they end, they will go to the next calls
        // if you wanna make it with virtual threads then see next method
        /*

            18:56:12.263 [pool-1-thread-3] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/3
18:56:12.260 [main] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- submitted
18:56:12.262 [pool-1-thread-2] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/2
18:56:12.262 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/1
18:56:13.310 [pool-1-thread-3] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-3 is: Durable Aluminum Hat
18:56:13.310 [pool-1-thread-2] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-2 is: Incredible Granite Bottle
18:56:13.310 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-1 is: Mediocre Linen Bench
18:56:13.315 [pool-1-thread-2] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/5
18:56:13.315 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/6
18:56:13.315 [pool-1-thread-3] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/4
18:56:14.331 [pool-1-thread-3] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-4 is: Sleek Silk Bag
18:56:14.331 [pool-1-thread-2] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-5 is: Gorgeous Granite Lamp
18:56:14.331 [pool-1-thread-3] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/7
18:56:14.331 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-6 is: Lightweight Linen Lamp
18:56:14.331 [pool-1-thread-2] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/8
18:56:14.332 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/9
18:56:15.342 [pool-1-thread-2] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-8 is: Enormous Silk Car
18:56:15.342 [pool-1-thread-1] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-9 is: Small Rubber Chair
18:56:15.342 [pool-1-thread-3] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-7 is: Fantastic Iron Knife
18:56:15.342 [pool-1-thread-2] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/10
18:56:16.357 [pool-1-thread-2] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-10 is: Synergistic Granite Shirt

         */

    }

    private static void fixedVirtualThreadPool(){
        var factory = Thread.ofVirtual().name("youness",1).factory();
        execute(Executors.newFixedThreadPool(3,factory),10);

        /*
            19:00:47.093 [youness2] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/2
19:00:47.093 [youness3] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/3
19:00:47.093 [main] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- submitted
19:00:47.093 [youness1] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/1
19:00:48.144 [youness1] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-1 is: Mediocre Linen Bench
19:00:48.146 [youness3] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-3 is: Durable Aluminum Hat
19:00:48.144 [youness2] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-2 is: Incredible Granite Bottle
19:00:48.149 [youness1] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/4
19:00:48.149 [youness2] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/6
19:00:48.149 [youness3] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/5
19:00:49.152 [youness1] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-4 is: Sleek Silk Bag
19:00:49.152 [youness3] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-5 is: Gorgeous Granite Lamp
19:00:49.152 [youness1] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/7
19:00:49.152 [youness3] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/8
19:00:49.152 [youness2] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-6 is: Lightweight Linen Lamp
19:00:49.152 [youness2] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/9
19:00:50.161 [youness3] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-8 is: Enormous Silk Car
19:00:50.161 [youness1] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-7 is: Fantastic Iron Knife
19:00:50.161 [youness3] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/10
19:00:50.161 [youness2] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-9 is: Small Rubber Chair
19:00:51.187 [youness3] INFO com.example.playgroundproject.executor_service.sec07.Lec05ConcurrencyLimit -- product-10 is: Synergistic Granite Shirt

         */

        // as you can see all 3 virtual threads are used again and again
        //virtual threads aren't supposed to be pooled
        // the solution is semaphore!
    }

}
