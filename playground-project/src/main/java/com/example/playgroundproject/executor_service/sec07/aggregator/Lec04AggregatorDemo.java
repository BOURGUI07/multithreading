package com.example.playgroundproject.executor_service.sec07.aggregator;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

@Slf4j
public class Lec04AggregatorDemo {
    public static void main(String[] args) throws Exception {
        aggregator3();
    }

    private static void aggregator() throws Exception {
        var service = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(service);
        log.info("Product-1 :{}",aggregator.getProduct(1));

        /*
            18:21:20.340 [virtual-24] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/rating/1
18:21:20.340 [virtual-22] INFO com.example.playgroundproject.executor_service.sec07.externalService.Client -- Calling external service: http://localhost:7070/sec01/product/1
18:21:21.387 [main] INFO com.example.playgroundproject.executor_service.sec07.aggregator.Lec04AggregatorDemo -- Product-1 :ProductDTO[id=1, description=Mediocre Linen Bench, rating=2]
         */
    }

    private static void aggregator2() throws Exception {
        var service = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(service);
        var futures = IntStream.rangeClosed(1,50)
                .mapToObj(id->service.submit(()->aggregator.getProduct(id)))
                .toList();

        futures.stream()
                .map(Lec04AggregatorDemo::toProductDTO)
                .forEach(x->log.info("PRODUCT-{}: {}",x.id(),x));

        // got all products in one sec

    }

    private static ProductDTO toProductDTO(Future<ProductDTO> future) {
        try {
            return future.get();
        }catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    //if you wanna configure the thread name then use this
    private static void aggregator3() {
        var service = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("YOUNESS-",1).factory());
        var aggregator = new AggregatorService(service);
        var futures = IntStream.rangeClosed(1,50)
                .mapToObj(id->service.submit(()->aggregator.getProduct(id)))
                .toList();

        futures.stream()
                .map(Lec04AggregatorDemo::toProductDTO)
                .forEach(x->log.info("PRODUCT-{}: {}",x.id(),x));

    }
}


