package com.example.playgroundproject.executor_service.sec07;

import com.example.playgroundproject.Util;
import com.example.playgroundproject.executor_service.sec07.externalService.Client;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ScheduledExecutorWithVirtualThreads {
    public static void main(String[] args) {
        demo();

        /*
            20:06:19.002 [virtual-23] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/1
20:06:20.007 [virtual-32] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/1
20:06:20.045 [virtual-23] INFO com.example.playgroundproject.executor_service.sec07.ScheduledExecutorWithVirtualThreads -- product-1: Mediocre Linen Bench
20:06:21.010 [virtual-35] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/1
20:06:21.028 [virtual-32] INFO com.example.playgroundproject.executor_service.sec07.ScheduledExecutorWithVirtualThreads -- product-1: Mediocre Linen Bench
20:06:22.025 [virtual-36] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/1
20:06:22.028 [virtual-35] INFO com.example.playgroundproject.executor_service.sec07.ScheduledExecutorWithVirtualThreads -- product-1: Mediocre Linen Bench
20:06:23.026 [virtual-37] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/1
20:06:23.045 [virtual-36] INFO com.example.playgroundproject.executor_service.sec07.ScheduledExecutorWithVirtualThreads -- product-1: Mediocre Linen Bench
20:06:24.038 [virtual-37] INFO com.example.playgroundproject.executor_service.sec07.ScheduledExecutorWithVirtualThreads -- product-1: Mediocre Linen Bench

         */
    }

    private static void demo() {
        var scheduler = Executors.newSingleThreadScheduledExecutor();
        var service = Executors.newVirtualThreadPerTaskExecutor();
        try (service;scheduler) {
            scheduler.scheduleWithFixedDelay(() -> {
                service.submit(()->task(1));
            }, 0, 1, TimeUnit.SECONDS); // once every second
            Util.sleepSeconds(5); //block for 5 secs
        }
    }

    private static void task(int id){
        log.info("product-{}: {}",id, Client.getProduct(id));
    }
}
