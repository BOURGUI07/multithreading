package com.example.playgroundproject.structured_concurrency.sec09;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class Lec02ThreadLocalProblem {
    public static void main(String[] args) {
        demo();
    }

    private static final ThreadLocal<String> SESSION_TOKEN = new ThreadLocal<>();

    // ** ---- below code is just to demonstrate the workflow --- **

    private static void processIncomingRequest(){
        authenticate();
        controller();
    }

    //authentication process
    private static void authenticate(){
        var token = UUID.randomUUID().toString();
        log.info("token={}", token);
        SESSION_TOKEN.set(token);
    }

    // Controller layer
    private static void controller(){
        log.info("controller: {}", SESSION_TOKEN.get());
        service();
    }

    // Service Layer
    private static void service(){
        log.info("service: {}", SESSION_TOKEN.get());
        Thread.ofVirtual().start(()->callExternalService());
        //started a child thread
    }

    // This is a client to call external service
    private static void callExternalService(){
        log.info("preparing HTTP request with token: {}", SESSION_TOKEN.get());
    }

    public static void demo() {

        Thread.ofVirtual().name("1").start( () -> processIncomingRequest());
        Thread.ofVirtual().name("2").start( () -> processIncomingRequest());

        Util.sleepSeconds(1);


        /*

09:49:17.396 [2] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec02ThreadLocalProblem -- token=72f74d11-331a-40ac-8300-b168a45e3c9b
09:49:17.396 [1] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec02ThreadLocalProblem -- token=ed3107a2-6dbb-492c-a8f5-b23e68e1ae80
09:49:17.405 [2] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec02ThreadLocalProblem -- controller: 72f74d11-331a-40ac-8300-b168a45e3c9b
09:49:17.405 [1] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec02ThreadLocalProblem -- controller: ed3107a2-6dbb-492c-a8f5-b23e68e1ae80
09:49:17.405 [2] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec02ThreadLocalProblem -- service: 72f74d11-331a-40ac-8300-b168a45e3c9b
09:49:17.405 [1] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec02ThreadLocalProblem -- service: ed3107a2-6dbb-492c-a8f5-b23e68e1ae80
09:49:17.405 [virtual-31] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec02ThreadLocalProblem -- preparing HTTP request with token: null
09:49:17.405 [virtual-30] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec02ThreadLocalProblem -- preparing HTTP request with token: null
         */

    }
}
