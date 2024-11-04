package com.example.playgroundproject.completabale_future.sec08;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class Lec07AnyOf {
    public static void main(String[] args) {
        demo();
    }

    private static CompletableFuture<String> getDeltaAirline(ExecutorService executorService) {
        return CompletableFuture.supplyAsync(()->{
            var random = ThreadLocalRandom.current().nextInt(100,1000);
            Util.sleepMillis(random);
            return "Delta$"+ random;
        },executorService);
    }

    private static CompletableFuture<String> getFrontierAirline(ExecutorService executorService) {
        return CompletableFuture.supplyAsync(()->{
            var random = ThreadLocalRandom.current().nextInt(100,1000);
            Util.sleepMillis(random);
            return "Frontier$"+ random;
        },executorService);
    }

    private static void demo(){
        try(var executor = Executors.newVirtualThreadPerTaskExecutor()){
            var cf1 = getDeltaAirline(executor);
            var cf2 = getFrontierAirline(executor);
            log.info("Airline: {}",CompletableFuture.anyOf(cf1,cf2).join());
        }

        //08:49:29.460 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec07AnyOf -- Airline: Delta$240

        //rerun

    }


}
