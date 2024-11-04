package com.example.playgroundproject.structured_concurrency.sec09;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class Lec04SuccessOrFailure {
    public static void main(String[] args) {
        demo3();
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
        try(var taskScope = new StructuredTaskScope<>()){
            var subTask1 = taskScope.fork(Lec04SuccessOrFailure::getFrontierAirfare);
            var subTask2 = taskScope.fork(Lec04SuccessOrFailure::getDeltaAirfare);

            taskScope.join();

            log.info("subtask1 state: {}",subTask1.state());
            log.info("subtask2 state: {}",subTask2.state());

            log.info("subtask1 result: {}",subTask1.get());
            log.info("subtask2 result: {}",subTask2.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /*
            10:33:34.916 [virtual-24] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec04SuccessOrFailure -- Delta: 235
10:33:34.916 [virtual-22] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec04SuccessOrFailure -- Frontier: 266
10:33:36.946 [main] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec04SuccessOrFailure -- subtask1 state: SUCCESS
10:33:36.946 [main] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec04SuccessOrFailure -- subtask2 state: SUCCESS
10:33:36.947 [main] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec04SuccessOrFailure -- subtask1 result: Frontier Airfare: 266
10:33:36.947 [main] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec04SuccessOrFailure -- subtask2 result: Delta Airfare: 235
         */
    }


    private static void demo2(){
        try(var taskScope = new StructuredTaskScope<>()){
            var subTask1 = taskScope.fork(Lec04SuccessOrFailure::getFrontierAirfare);
            var subTask2 = taskScope.fork(Lec04SuccessOrFailure::getDeltaAirfare);

            taskScope.joinUntil(Instant.now().minusMillis(1500));

            log.info("subtask1 state: {}",subTask1.state());
            log.info("subtask2 state: {}",subTask2.state());

            log.info("subtask1 result: {}",subTask1.get());
            log.info("subtask2 result: {}",subTask2.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /*
            10:35:16.355 [virtual-22] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec04SuccessOrFailure -- Frontier: 264
10:35:16.355 [virtual-24] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec04SuccessOrFailure -- Delta: 803
10:35:16.369 [virtual-22] ERROR com.example.playgroundproject.Util -- Cancelling task: Frontier
10:35:16.369 [virtual-24] ERROR com.example.playgroundproject.Util -- Cancelling task: Delta
Exception in thread "main" java.lang.RuntimeException: java.util.concurrent.TimeoutException
	at com.example.playgroundproject.structured_concurrency.sec09.Lec04SuccessOrFailure.demo2(Lec04SuccessOrFailure.java:71)
	at com.example.playgroundproject.structured_concurrency.sec09.Lec04SuccessOrFailure.main(Lec04SuccessOrFailure.java:14)
Caused by: java.util.concurrent.TimeoutException
	at java.base/jdk.internal.misc.ThreadFlock.awaitAll(ThreadFlock.java:362)
	at java.base/java.util.concurrent.StructuredTaskScope.implJoin(StructuredTaskScope.java:619)
	at java.base/java.util.concurrent.StructuredTaskScope.joinUntil(StructuredTaskScope.java:685)
	at com.example.playgroundproject.structured_concurrency.sec09.Lec04SuccessOrFailure.demo2(Lec04SuccessOrFailure.java:63)
	... 1 more
         */
    }

    private static void demo3(){
        try(var taskScope = new StructuredTaskScope<>()){
            var subTask1 = taskScope.fork(Lec04SuccessOrFailure::getFrontierAirfare);
            var subTask2 = taskScope.fork(Lec04SuccessOrFailure::failTask);

            taskScope.join();

            log.info("subtask1 state: {}",subTask1.state());
            log.info("subtask2 state: {}",subTask2.state());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /*
            10:38:15.303 [virtual-22] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec04SuccessOrFailure -- Frontier: 158
10:38:17.337 [main] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec04SuccessOrFailure -- subtask1 state: SUCCESS
10:38:17.337 [main] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec04SuccessOrFailure -- subtask2 state: FAILED
         */
    }

}
