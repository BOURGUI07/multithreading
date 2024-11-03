package com.example.playgroundproject.executor_service.sec07.externalService;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Lec03AccessResponseUsingFuture {
    public static void main(String[] args) throws Exception {
        getProduct2();
    }

    private static void getProduct() throws Exception {
        try(var service = Executors.newVirtualThreadPerTaskExecutor()) {
            var future = service.submit(() -> Client.getProduct(1));
            log.info("Product-1: {}",future.get());
        }

        /*
            17:38:00.040 [virtual-22] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/1
17:38:01.085 [main] INFO com.example.playgroundproject.executor_service.sec07.externalService.Lec03AccessResponseUsingFuture -- Product-1: Mediocre Linen Bench

         */
    }

    private static void getProduct2() throws Exception {
        try(var service = Executors.newVirtualThreadPerTaskExecutor()) {
            var future1 = service.submit(() -> Client.getProduct(1));
            var future2 = service.submit(() -> Client.getProduct(2));
            var future3 = service.submit(() -> Client.getProduct(3));
            log.info("Product-1: {}",future1.get());
            log.info("Product-2: {}",future2.get());
            log.info("Product-3: {}",future3.get());
        }

        //since we make 3 calls in parallel, we'll get all products in 1 sec

        /*
            17:42:12.646 [virtual-22] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/1
17:42:12.646 [virtual-26] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/3
17:42:12.646 [virtual-24] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/2
17:42:13.700 [main] INFO com.example.playgroundproject.executor_service.sec07.externalService.Lec03AccessResponseUsingFuture -- Product-1: Mediocre Linen Bench
17:42:13.705 [main] INFO com.example.playgroundproject.executor_service.sec07.externalService.Lec03AccessResponseUsingFuture -- Product-2: Incredible Granite Bottle
17:42:13.705 [main] INFO com.example.playgroundproject.executor_service.sec07.externalService.Lec03AccessResponseUsingFuture -- Product-3: Durable Aluminum Hat
         */
    }

    private static void getProduct3() throws Exception {
        try(var service = Executors.newVirtualThreadPerTaskExecutor()) {
            var future = service.submit(() -> Client.getProduct(1));
            log.info("Product-1: {}",future.get(2, TimeUnit.SECONDS));
            // wait max for 2 seconds otherwise timeout exception
        }
    }
}
