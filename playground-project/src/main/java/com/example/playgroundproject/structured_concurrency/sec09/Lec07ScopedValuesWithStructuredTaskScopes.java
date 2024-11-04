package com.example.playgroundproject.structured_concurrency.sec09;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class Lec07ScopedValuesWithStructuredTaskScopes {
    private static final ScopedValue<String> SESSION_TOKEN = ScopedValue.newInstance();
    public static void main(String[] args) {
        demo();
    }

    private static String getDeltaAirfare(){
        log.info("Token: {}",SESSION_TOKEN.get());

        var random = ThreadLocalRandom.current().nextInt(100,1000);
        log.info("Delta: {}",random);
        Util.sleepWithTask("Delta",1);
        return "Delta Airfare: "+random;
    }

    private static String getFrontierAirfare(){
        log.info("Token: {}",SESSION_TOKEN.get());

        var random = ThreadLocalRandom.current().nextInt(100,1000);
        log.info("Frontier: {}",random);
        Util.sleepWithTask("Frontier",2);
        return "Frontier Airfare: "+random;
    }

    private static String failTask(){
        throw new RuntimeException("oops");
    }

    private static void task(){

        try(var taskScope = new StructuredTaskScope<>()){
            log.info("Token: {}",SESSION_TOKEN.get());

            var subTask1 = taskScope.fork(Lec07ScopedValuesWithStructuredTaskScopes::getFrontierAirfare);
            var subTask2 = taskScope.fork(Lec07ScopedValuesWithStructuredTaskScopes::getDeltaAirfare);

            taskScope.join();

            log.info("subtask1 state: {}",subTask1.state());
            log.info("subtask2 state: {}",subTask2.state());

            log.info("subtask1 result: {}",subTask1.get());
            log.info("subtask2 result: {}",subTask2.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    private static void demo(){
        ScopedValue.runWhere(SESSION_TOKEN,"non-actual-token", Lec07ScopedValuesWithStructuredTaskScopes::task);
    }


}
