package com.example.playgroundproject.structured_concurrency.sec09;

import com.example.playgroundproject.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class Lec03ScopedValues {
    private static final ScopedValue<String> SESSION_TOKEN = ScopedValue.newInstance();
    public static void main(String[] args) {
        demo3();
    }

    private static void demo(){
        log.info("isBound: {}", SESSION_TOKEN.isBound());
        //if it's not bound then that means it doesn't have any value
        // we have to set the value before accessing the get() method
        // otherwise we'll get an exception

        log.info("value: {}", SESSION_TOKEN.orElse("Default Value"));
        //if no value is set, then return a default value

    }

    // ** ---- below code is just to demonstrate the workflow --- **

    //authentication process
    private static String authenticate(){
        var token = UUID.randomUUID().toString();
        log.info("token={}", token);
        return token;
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
        //if you don't want the callExternalService to see the actual token

        //ScopedValue.runWhere(SESSION_TOKEN, "new-token-" + Thread.currentThread().getName(),()->callExternalService());
        //even though you replace the actual token, the service will be able to see the actual token value afterwards
    }

    // This is a client to call external service
    private static void callExternalService(){
        log.info("preparing HTTP request with token: {}", SESSION_TOKEN.get());
    }

    private static void processIncomingRequest(){
        var token = authenticate();
        ScopedValue.runWhere(SESSION_TOKEN,token,()->controller());
    }

   private static void demo2(){
       log.info("isBound: {}", SESSION_TOKEN.isBound());
       log.info("value: {}", SESSION_TOKEN.orElse("Default Value"));
       processIncomingRequest();

       /*
        10:08:28.054 [main] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec03ScopedValues -- isBound: false
10:08:28.064 [main] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec03ScopedValues -- value: Default Value
10:08:28.130 [main] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec03ScopedValues -- token=8a61beb3-4179-4c96-8287-887673f958ab
10:08:28.131 [main] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec03ScopedValues -- controller: 8a61beb3-4179-4c96-8287-887673f958ab
10:08:28.131 [main] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec03ScopedValues -- service: 8a61beb3-4179-4c96-8287-887673f958ab
10:08:28.132 [main] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec03ScopedValues -- preparing HTTP request with token: 8a61beb3-4179-4c96-8287-887673f958ab
        */
   }

   private static void demo3(){
        Thread.ofVirtual().name("1").start(Lec03ScopedValues::processIncomingRequest);
        Thread.ofVirtual().name("2").start(Lec03ScopedValues::processIncomingRequest);
       Util.sleepSeconds(1);

       /*
        10:11:25.514 [1] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec03ScopedValues -- token=07fefcbc-804b-451a-a493-64bdec7b88ae
10:11:25.514 [2] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec03ScopedValues -- token=0bc6a3cb-3c2d-44eb-a2cc-e9f0fc6ab643
10:11:25.527 [1] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec03ScopedValues -- controller: 07fefcbc-804b-451a-a493-64bdec7b88ae
10:11:25.527 [2] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec03ScopedValues -- controller: 0bc6a3cb-3c2d-44eb-a2cc-e9f0fc6ab643
10:11:25.527 [1] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec03ScopedValues -- service: 07fefcbc-804b-451a-a493-64bdec7b88ae
10:11:25.527 [2] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec03ScopedValues -- service: 0bc6a3cb-3c2d-44eb-a2cc-e9f0fc6ab643
10:11:25.527 [1] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec03ScopedValues -- preparing HTTP request with token: 07fefcbc-804b-451a-a493-64bdec7b88ae
10:11:25.527 [2] INFO com.example.playgroundproject.structured_concurrency.sec09.Lec03ScopedValues -- preparing HTTP request with token: 0bc6a3cb-3c2d-44eb-a2cc-e9f0fc6ab643

        */
   }




}
