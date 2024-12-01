package com.jeet.ratelimiter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Connection;

public class TestRedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis://default:j8c41J9Mtzoj357t3ZPXKwxI0zyu0yKD@redis-11558.c282.east-us-mz.azure.redns.redis-cloud.com:11558");
        Connection connection = jedis.getConnection();
        System.out.printf("connection"+connection.getStatusCodeReply());
    }
}