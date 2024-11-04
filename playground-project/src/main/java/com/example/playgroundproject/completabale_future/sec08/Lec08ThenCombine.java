package com.example.playgroundproject.completabale_future.sec08;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

//return the best deal of both airfares
@Slf4j
public class Lec08ThenCombine {
    public static void main(String[] args) {
        demo();
    }

    record Airfare(String name, int amount){}

    private static CompletableFuture<Airfare> getDeltaAirline(ExecutorService executorService) {
        return CompletableFuture.supplyAsync(()->{
            var random = ThreadLocalRandom.current().nextInt(100,1000);
            Util.sleepMillis(random);
            log.info("Delta Airline: {}", random);
            return new Airfare("Delta",random);
        },executorService);
    }

    private static CompletableFuture<Airfare> getFrontierAirline(ExecutorService executorService) {
        return CompletableFuture.supplyAsync(()->{
            var random = ThreadLocalRandom.current().nextInt(100,1000);
            Util.sleepMillis(random);
            log.info("Frontier Airline: {}", random);
            return new Airfare("Frontier",random);
        },executorService);
    }

    private static void demo(){
        try(var executor = Executors.newVirtualThreadPerTaskExecutor()){
            var cf1 = getDeltaAirline(executor);
            var cf2 = getFrontierAirline(executor);
            var cf3 =  cf2.thenCombine(cf1,(a,b) -> a.amount<=b.amount? a:b)
                    .thenApply(af-> new Airfare(af.name(),(int) (af.amount()*0.9))) //apply discount
                    .join();
            log.info("Best Airfare Deal: {}",cf3);

        }

        /*
            09:01:35.805 [virtual-24] INFO com.example.playgroundproject.completabale_future.sec08.Lec08ThenCombine -- Frontier Airline: 379
09:01:36.136 [virtual-22] INFO com.example.playgroundproject.completabale_future.sec08.Lec08ThenCombine -- Delta Airline: 710
09:01:36.136 [main] INFO com.example.playgroundproject.completabale_future.sec08.Lec08ThenCombine -- Best Airfare Deal: Airfare[name=Frontier, amount=341]
         */

    }
}
