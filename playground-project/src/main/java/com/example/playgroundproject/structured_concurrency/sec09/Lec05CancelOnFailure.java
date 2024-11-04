package com.example.playgroundproject.structured_concurrency.sec09;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class Lec05CancelOnFailure {
    public static void main(String[] args) {
        demo1();
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
        try(var taskScope = new StructuredTaskScope.ShutdownOnFailure()){
            var subTask1 = taskScope.fork(Lec05CancelOnFailure::getFrontierAirfare);
            var subTask2 = taskScope.fork(Lec05CancelOnFailure::failTask);

            taskScope.join();

            log.info("subtask1 state: {}",subTask1.state());
            log.info("subtask2 state: {}",subTask2.state());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /*
            10:43:32.570 [virtual-22] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec05CancelOnFailure -- Frontier: 843
10:43:32.570 [main] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec05CancelOnFailure -- subtask1 state: UNAVAILABLE
10:43:32.580 [main] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec05CancelOnFailure -- subtask2 state: FAILED
10:43:32.582 [virtual-22] ERROR com.example.playgroundproject.Util -- Cancelling task: Frontier
         */


    }

    private static void demo1(){
        try(var taskScope = new StructuredTaskScope.ShutdownOnFailure()){
            var subTask1 = taskScope.fork(Lec05CancelOnFailure::getFrontierAirfare);
            var subTask2 = taskScope.fork(Lec05CancelOnFailure::failTask);

            taskScope.join();
            taskScope.throwIfFailed();

            log.info("subtask1 state: {}",subTask1.state());
            log.info("subtask2 state: {}",subTask2.state());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /*
            10:45:54.496 [virtual-22] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec05CancelOnFailure -- Frontier: 279
10:45:54.513 [virtual-22] ERROR com.example.playgroundproject.Util -- Cancelling task: Frontier
Exception in thread "main" java.lang.RuntimeException: java.util.concurrent.ExecutionException: java.lang.RuntimeException: oops
	at com.example.playgroundproject.structured_concurrency.sec09.Lec05CancelOnFailure.demo1(Lec05CancelOnFailure.java:69)
	at com.example.playgroundproject.structured_concurrency.sec09.Lec05CancelOnFailure.main(Lec05CancelOnFailure.java:12)
Caused by: java.util.concurrent.ExecutionException: java.lang.RuntimeException: oops
	at java.base/java.util.concurrent.StructuredTaskScope$ShutdownOnFailure.throwIfFailed(StructuredTaskScope.java:1318)
	at java.base/java.util.concurrent.StructuredTaskScope$ShutdownOnFailure.throwIfFailed(StructuredTaskScope.java:1295)
	at com.example.playgroundproject.structured_concurrency.sec09.Lec05CancelOnFailure.demo1(Lec05CancelOnFailure.java:63)
	... 1 more
Caused by: java.lang.RuntimeException: oops
	at com.example.playgroundproject.structured_concurrency.sec09.Lec05CancelOnFailure.failTask(Lec05CancelOnFailure.java:30)
	at java.base/java.util.concurrent.StructuredTaskScope$SubtaskImpl.run(StructuredTaskScope.java:889)
	at java.base/java.lang.VirtualThread.run(VirtualThread.java:329)
         */


    }
}
