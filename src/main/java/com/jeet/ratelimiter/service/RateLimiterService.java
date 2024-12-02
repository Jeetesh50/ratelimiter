package com.jeet.ratelimiter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Service
public class RateLimiterService {
    private static final int BUCKET_CAPACITY = 10;  // Max tokens
    private static final int REFILL_RATE = 1;      // Tokens per second

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public synchronized boolean isAllowed(String userId) {
        String bucketKey = "rate:limiter:" + userId;
        long currentTime = Instant.now().getEpochSecond();

        // Fetch bucket info from Redis
        String bucketData = redisTemplate.opsForValue().get(bucketKey);

        int tokens;
        long lastRefillTime;

        if (bucketData == null) {
            tokens = BUCKET_CAPACITY;  // Initialize with full tokens
            lastRefillTime = currentTime;
        } else {
            String[] parts = bucketData.split(":");
            tokens = Integer.parseInt(parts[0]);
            lastRefillTime = Long.parseLong(parts[1]);

            // Refill tokens based on elapsed time
            long timeElapsed = currentTime - lastRefillTime;
            int newTokens = (int) (timeElapsed * REFILL_RATE);
            tokens = Math.min(BUCKET_CAPACITY, tokens + newTokens);  // Cap at BUCKET_CAPACITY
            lastRefillTime += timeElapsed;  // Update last refill time
        }

        if (tokens > 0) {
            tokens--;  // Consume a token
            // Atomically update bucket state in Redis
            redisTemplate.opsForValue().set(bucketKey, tokens + ":" + lastRefillTime, 1, TimeUnit.HOURS);
            return true;  // Request allowed
        }

        return false;  // Request throttled
    }
}