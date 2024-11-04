package com.example.playgroundproject.completabale_future.sec08;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Slf4j
public class Lec03SupplyAsync {
    public static void main(String[] args) {
        demo();
    }

    private static CompletableFuture<String> slowTask() {
        log.info("slowTask started");
        var cf = CompletableFuture.supplyAsync(()->{
            Util.sleepSeconds(1);
            return "Hello World";
        }, Executors.newVirtualThreadPerTaskExecutor());
        log.info("slowTask ended");
        return cf;
    }

    private static void demo() {
        log.info("demo started");
        var cf = slowTask();
        cf.thenAccept(value->log.info("value: {}",value));
        log.info("demo ended");
        Util.sleepSeconds(2);

        /*
            08:00:27.437 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec03SupplyAsync -- demo started
08:00:27.443 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec03SupplyAsync -- slowTask started
08:00:27.447 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec03SupplyAsync -- slowTask ended
08:00:27.447 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec03SupplyAsync -- demo ended
08:00:28.370 [virtual-22] INFO com.example.playgroundproject.completabale_future.sec08.Lec03SupplyAsync -- value: Hello World
         */
    }
}
