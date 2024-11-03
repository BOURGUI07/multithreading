package com.example.playgroundproject.executor_service.sec07;

import com.example.playgroundproject.executor_service.sec07.aggregator.ProductDTO;
import com.example.playgroundproject.executor_service.sec07.concurrent_limiter.ConcurrencyLimiter;
import com.example.playgroundproject.executor_service.sec07.externalService.Client;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Lec06ConcurrencyLimitWithSemaphore {
    public static void main(String[] args) throws Exception {
        demo1();
    }

    public static String printProduct(int id){
        var product = Client.getProduct(id);
        log.info("product-{} is: {}", id, product);
        return product;
    }

    private static void execute(ConcurrencyLimiter limiter, int count) throws Exception {
        try (limiter) {
            for (int i = 1; i <= count; i++) {
                var j = i;
                limiter.execute(() -> printProduct(j));
            }
            log.info("submitted");
        }
    }

    private static void demo() throws Exception {
        var limiter = new ConcurrencyLimiter(Executors.newVirtualThreadPerTaskExecutor(),3);
        execute(limiter,10);
    }

    private static void demo1() throws Exception {
        var factory = Thread.ofVirtual().name("youness",1).factory();
        var limiter = new ConcurrencyLimiter(Executors.newThreadPerTaskExecutor(factory),3);
        execute(limiter,10);

        //as you can see now virtual threads aren't pooled now, they're disposable
        //if you limit to one request per call, then there will be a task order problem
        // below we allowed 3 concurrent requests per call, even if you see the first are unordered, they're still concurrent

        /*
            19:33:44.109 [main] INFO com.example.playgroundproject.executor_service.sec07.Lec06ConcurrencyLimitWithSemaphore -- submitted
19:33:44.111 [youness1] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/1
19:33:44.111 [youness3] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/3
19:33:44.111 [youness2] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/2
19:33:45.150 [youness3] INFO com.example.playgroundproject.executor_service.sec07.Lec06ConcurrencyLimitWithSemaphore -- product-3 is: Durable Aluminum Hat
19:33:45.151 [youness2] INFO com.example.playgroundproject.executor_service.sec07.Lec06ConcurrencyLimitWithSemaphore -- product-2 is: Incredible Granite Bottle
19:33:45.150 [youness1] INFO com.example.playgroundproject.executor_service.sec07.Lec06ConcurrencyLimitWithSemaphore -- product-1 is: Mediocre Linen Bench
19:33:45.154 [youness4] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/4
19:33:45.154 [youness5] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/5
19:33:45.154 [youness6] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/6
19:33:46.169 [youness4] INFO com.example.playgroundproject.executor_service.sec07.Lec06ConcurrencyLimitWithSemaphore -- product-4 is: Sleek Silk Bag
19:33:46.169 [youness6] INFO com.example.playgroundproject.executor_service.sec07.Lec06ConcurrencyLimitWithSemaphore -- product-6 is: Lightweight Linen Lamp
19:33:46.169 [youness5] INFO com.example.playgroundproject.executor_service.sec07.Lec06ConcurrencyLimitWithSemaphore -- product-5 is: Gorgeous Granite Lamp
19:33:46.169 [youness7] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/7
19:33:46.169 [youness8] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/8
19:33:46.169 [youness9] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/9
19:33:47.180 [youness9] INFO com.example.playgroundproject.executor_service.sec07.Lec06ConcurrencyLimitWithSemaphore -- product-9 is: Small Rubber Chair
19:33:47.180 [youness8] INFO com.example.playgroundproject.executor_service.sec07.Lec06ConcurrencyLimitWithSemaphore -- product-8 is: Enormous Silk Car
19:33:47.180 [youness10] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/10
19:33:47.180 [youness7] INFO com.example.playgroundproject.executor_service.sec07.Lec06ConcurrencyLimitWithSemaphore -- product-7 is: Fantastic Iron Knife
19:33:48.194 [youness10] INFO com.example.playgroundproject.executor_service.sec07.Lec06ConcurrencyLimitWithSemaphore -- product-10 is: Synergistic Granite Shirt

         */
    }

}


