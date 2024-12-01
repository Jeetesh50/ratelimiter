package com.jeet.ratelimiter.controller;

import com.jeet.ratelimiter.service.RateLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimiterController {

    @Autowired
    private RateLimiterService rateLimiterService;

    @GetMapping("/api/request")
    public String handleRequest(@RequestParam String userId) {
        if (rateLimiterService.isAllowed(userId)) {
            return "Request processed successfully!";
        } else {
            return "Rate limit exceeded. Try again later.";
        }
    }
}