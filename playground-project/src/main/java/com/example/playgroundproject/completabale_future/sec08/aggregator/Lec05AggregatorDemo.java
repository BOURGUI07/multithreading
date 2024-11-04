package com.example.playgroundproject.completabale_future.sec08.aggregator;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;

@Slf4j
public class Lec05AggregatorDemo {
    public static void main(String[] args) throws Exception {
        demo3();
    }

    private static void demo() throws Exception {
        var service = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(service);
        log.info("Product-1: {}",aggregator.getProduct(1));

        /*
            08:18:49.816 [virtual-22] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/1
08:18:49.816 [virtual-24] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/1
08:18:50.860 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec05AggregatorDemo -- Product-1: ProductDTO[id=1, description=Practical Marble Shoes, rating=2]
         */
    }

    private static void demo1() throws Exception {
        var service = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(service);
        log.info("Product-1: {}",aggregator.getProduct(51));

        /*
            08:23:31.355 [virtual-22] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/51
08:23:31.355 [virtual-24] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/51
08:23:32.389 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec05AggregatorDemo -- Product-1: ProductDTO[id=51, description=Lightweight Granite Knife, rating=-1]
         */
    }

    private static void demo2() throws Exception {
        var service = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(service);
        log.info("Product-1: {}",aggregator.getProduct(52));

        /*
            08:25:39.176 [virtual-22] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/52
08:25:39.176 [virtual-25] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/52
08:25:39.208 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec05AggregatorDemo -- Product-1: ProductDTO[id=52, description=product-not-found, rating=-1]
         */
    }

    private static void demo3() throws Exception {
        var service = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(service);
        log.info("Product-1: {}",aggregator.getProduct(50));

        /*
            08:29:11.040 [virtual-25] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/50
08:29:11.040 [virtual-22] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/50
08:29:11.787 [CompletableFutureDelayScheduler] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.AggregatorService -- Error: null
08:29:12.074 [main] INFO com.example.playgroundproject.completabale_future.sec08.aggregator.Lec05AggregatorDemo -- Product-1: ProductDTO[id=50, description=Aerodynamic Copper Computer, rating=null]
         */
    }





}
