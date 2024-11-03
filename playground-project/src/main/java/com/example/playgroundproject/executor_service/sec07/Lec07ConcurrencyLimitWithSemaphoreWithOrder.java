package com.example.playgroundproject.executor_service.sec07;

import com.example.playgroundproject.executor_service.sec07.concurrent_limiter.ConcurrencyLimiter;
import com.example.playgroundproject.executor_service.sec07.concurrent_limiter.ConcurrencyLimiterWithOrder;
import com.example.playgroundproject.executor_service.sec07.externalService.Client;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;

@Slf4j
public class Lec07ConcurrencyLimitWithSemaphoreWithOrder {
    public static void main(String[] args) throws Exception {
        demo1();
    }

    public static String printProduct(int id){
        var product = Client.getProduct(id);
        log.info("product-{} is: {}", id, product);
        return product;
    }

    private static void execute(ConcurrencyLimiterWithOrder limiter, int count) throws Exception {
        try (limiter) {
            for (int i = 1; i <= count; i++) {
                var j = i;
                limiter.execute(() -> printProduct(j));
            }
            log.info("submitted");
        }
    }

    private static void demo() throws Exception {
        var limiter = new ConcurrencyLimiterWithOrder(Executors.newVirtualThreadPerTaskExecutor(),3);
        execute(limiter,10);
    }

    private static void demo1() throws Exception {
        var factory = Thread.ofVirtual().name("youness",1).factory();
        var limiter = new ConcurrencyLimiterWithOrder(Executors.newThreadPerTaskExecutor(factory),1);
        execute(limiter,10);


        /*

         */
    }

}


