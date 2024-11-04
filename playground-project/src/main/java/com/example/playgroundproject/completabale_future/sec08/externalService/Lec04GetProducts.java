package com.example.playgroundproject.completabale_future.sec08.externalService;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Lec04GetProducts {
    public static void main(String[] args) throws Exception {
        getProduct2();
    }


    private static void getProduct2() throws Exception {
        try(var service = Executors.newVirtualThreadPerTaskExecutor()) {

            var cf1 = CompletableFuture.supplyAsync(()->Client.getProduct(1),service);
            var cf2 = CompletableFuture.supplyAsync(()->Client.getProduct(2),service);
            var cf3 = CompletableFuture.supplyAsync(()->Client.getProduct(3),service);

            log.info("Product-1: {}",cf1.get());
            log.info("Product-2: {}",cf2.get());
            log.info("Product-3: {}",cf3.get());
        }

        /*
            08:09:22.983 [virtual-24] INFO com.example.playgroundproject.completabale_future.sec08.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/2
08:09:22.983 [virtual-22] INFO com.example.playgroundproject.completabale_future.sec08.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/1
08:09:22.983 [virtual-26] INFO com.example.playgroundproject.completabale_future.sec08.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/3
08:09:24.095 [main] INFO com.example.playgroundproject.completabale_future.sec08.externalService.Lec04GetProducts -- Product-1: Practical Marble Shoes
08:09:24.095 [main] INFO com.example.playgroundproject.completabale_future.sec08.externalService.Lec04GetProducts -- Product-2: Awesome Granite Bottle
08:09:24.095 [main] INFO com.example.playgroundproject.completabale_future.sec08.externalService.Lec04GetProducts -- Product-3: Synergistic Aluminum Knife

         */

    }

}
