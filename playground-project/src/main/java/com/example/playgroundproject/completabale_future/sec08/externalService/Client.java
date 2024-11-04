package com.example.playgroundproject.completabale_future.sec08.externalService;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
public class Client {
    private static final String PRODUCT_SERVICE_URL = "http://localhost:7070/sec01/product/";
    private static final String RATING_SERVICE_URL = "http://localhost:7070/sec01/rating/";

    private static String callExternalService(String url) {
        log.info("Calling external service: " + url);
        try(var stream  = URI.create(url).toURL().openStream()){
            return new String(stream.readAllBytes());
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String getProduct(int id){
        return callExternalService(PRODUCT_SERVICE_URL + id);
    }

    public static Integer getRating(int id){
        return Integer.parseInt(callExternalService(RATING_SERVICE_URL + id));
    }
}
