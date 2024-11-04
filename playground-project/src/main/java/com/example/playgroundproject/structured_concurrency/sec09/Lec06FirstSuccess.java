package com.example.playgroundproject.structured_concurrency.sec09;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class Lec06FirstSuccess {
    public static void main(String[] args) {
        demo2();
    }

    private static String getDeltaAirfare(){
        var random = ThreadLocalRandom.current().nextInt(100,1000);
        log.info("Delta: {}",random);
        Util.sleepWithTask("Delta",1);
        return "Delta Airfare: "+random;
    }

    private static String getFrontierAirfare(){
        var random = ThreadLocalRandom.current().nextInt(100,1000);
        log.info("Frontier: {}",random);
        Util.sleepWithTask("Frontier",2);
        return "Frontier Airfare: "+random;
    }

    private static String failTask(){
        throw new RuntimeException("oops");
    }

    private static void demo(){
        try(var taskScope = new StructuredTaskScope.ShutdownOnSuccess<>()){
            var subTask1 = taskScope.fork(Lec06FirstSuccess::getFrontierAirfare);
            var subTask2 = taskScope.fork(Lec06FirstSuccess::getDeltaAirfare);

            taskScope.join();

            log.info("subtask1 state: {}",subTask1.state());
            log.info("subtask2 state: {}",subTask2.state());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /*
            10:52:33.933 [virtual-22] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec06FirstSuccess -- Frontier: 494
10:52:33.933 [virtual-24] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec06FirstSuccess -- Delta: 118
10:52:34.970 [main] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec06FirstSuccess -- subtask1 state: UNAVAILABLE
10:52:34.970 [virtual-22] ERROR com.example.playgroundproject.Util -- Cancelling task: Frontier
10:52:34.971 [main] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec06FirstSuccess -- subtask2 state: SUCCESS
         */


    }

    private static void demo2(){
        try(var taskScope = new StructuredTaskScope.ShutdownOnSuccess<>()){
            var subTask1 = taskScope.fork(Lec06FirstSuccess::getFrontierAirfare);
            var subTask2 = taskScope.fork(Lec06FirstSuccess::getDeltaAirfare);

            taskScope.join();

            log.info("subtask1 state: {}",subTask1.state());
            log.info("subtask2 state: {}",subTask2.state());

            log.info("subtask result: {}",taskScope.result(ex->new RuntimeException("all failed")));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /*
            10:53:16.285 [virtual-24] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec06FirstSuccess -- Delta: 610
10:53:16.285 [virtual-22] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec06FirstSuccess -- Frontier: 613
10:53:17.328 [main] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec06FirstSuccess -- subtask1 state: UNAVAILABLE
10:53:17.328 [virtual-22] ERROR com.example.playgroundproject.Util -- Cancelling task: Frontier
10:53:17.328 [main] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec06FirstSuccess -- subtask2 state: SUCCESS
10:53:17.329 [main] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec06FirstSuccess -- subtask result: Delta Airfare: 610
         */
    }
}
