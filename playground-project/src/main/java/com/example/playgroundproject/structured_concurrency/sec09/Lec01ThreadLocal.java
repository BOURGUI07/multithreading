package com.example.playgroundproject.structured_concurrency.sec09;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class Lec01ThreadLocal {
    public static void main(String[] args) {
        demo();
    }

    private static final ThreadLocal<String> SESSION_TOKEN = new ThreadLocal<>();

    public static void demo() {

        Thread.ofVirtual().name("1").start( () -> processIncomingRequest());
        Thread.ofVirtual().name("2").start( () -> processIncomingRequest());

        Util.sleepSeconds(1);

        /*
            09:48:54.841 [1] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec01ThreadLocal -- token=d327a853-fc89-4228-978a-99babe6e64a5
09:48:54.841 [2] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec01ThreadLocal -- token=bb0ec8ef-78b3-4c0b-bc7f-0a4cb5649196
09:48:54.851 [1] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec01ThreadLocal -- controller: d327a853-fc89-4228-978a-99babe6e64a5
09:48:54.851 [2] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec01ThreadLocal -- controller: bb0ec8ef-78b3-4c0b-bc7f-0a4cb5649196
09:48:54.851 [2] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec01ThreadLocal -- service: bb0ec8ef-78b3-4c0b-bc7f-0a4cb5649196
09:48:54.851 [2] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec01ThreadLocal -- preparing HTTP request with token: bb0ec8ef-78b3-4c0b-bc7f-0a4cb5649196
09:48:54.851 [1] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec01ThreadLocal -- service: d327a853-fc89-4228-978a-99babe6e64a5
09:48:54.851 [1] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec01ThreadLocal -- preparing HTTP request with token: d327a853-fc89-4228-978a-99babe6e64a5
         */

    }

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
        callExternalService();
    }

    // This is a client to call external service
    private static void callExternalService(){
        log.info("preparing HTTP request with token: {}", SESSION_TOKEN.get());
    }
}
