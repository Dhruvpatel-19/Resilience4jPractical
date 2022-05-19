package com.example.ServiceA.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RestController
@RequestMapping("/a")
public class ServiceAController {

    @Autowired
    private RestTemplate restTemplate;

    public static final String BASE_URL = "http://localhost:8081/";

    public static final String SERVICE_A = "serviceA";

    int count=1;

    @GetMapping
    //@CircuitBreaker(name = SERVICE_A , fallbackMethod = "serviceAFallBack")
    //@Retry(name = SERVICE_A  , fallbackMethod = "serviceAFallBack")
    @RateLimiter(name = SERVICE_A , fallbackMethod = "serviceAFallBack")
    public String ServiceA(){
        //System.out.println("Retry method called " + count++ + " times at " + new Date()); //use when Retry is used
        return restTemplate.getForObject(BASE_URL+"b" , String.class);
    }

    public String serviceAFallBack(Exception e){
       return "This is a fallback method for Service A";
    }
}
