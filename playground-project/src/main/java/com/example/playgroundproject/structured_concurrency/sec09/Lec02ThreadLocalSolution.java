package com.example.playgroundproject.structured_concurrency.sec09;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class Lec02ThreadLocalSolution {
    public static void main(String[] args) {
        demo();
    }

    private static final ThreadLocal<String> SESSION_TOKEN = new InheritableThreadLocal<>();

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
        var childThread = "Child-of-"  + Thread.currentThread().getName();
        Thread.ofVirtual().name(childThread).start(()->callExternalService());
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

            09:52:36.515 [1] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec02ThreadLocalSolution -- token=77eaf733-484a-41f6-8b84-4e76bdb90610
09:52:36.515 [2] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec02ThreadLocalSolution -- token=e0631e82-f27c-4e7c-86bb-ec9183563b16
09:52:36.526 [1] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec02ThreadLocalSolution -- controller: 77eaf733-484a-41f6-8b84-4e76bdb90610
09:52:36.526 [1] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec02ThreadLocalSolution -- service: 77eaf733-484a-41f6-8b84-4e76bdb90610
09:52:36.526 [2] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec02ThreadLocalSolution -- controller: e0631e82-f27c-4e7c-86bb-ec9183563b16
09:52:36.526 [2] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec02ThreadLocalSolution -- service: e0631e82-f27c-4e7c-86bb-ec9183563b16
09:52:36.526 [Child-of-1] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec02ThreadLocalSolution -- preparing HTTP request with token: 77eaf733-484a-41f6-8b84-4e76bdb90610
09:52:36.526 [Child-of-2] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec02ThreadLocalSolution -- preparing HTTP request with token: e0631e82-f27c-4e7c-86bb-ec9183563b16
         */

    }
}
