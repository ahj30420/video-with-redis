package com.project.mytv.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

@TestConfiguration
public class TestRedisConfig {

    private RedisServer redisServer;

    public TestRedisConfig(
            @Value("${spring.data.redis.port}")
            Integer redisPort
    ) {
        try {
            this.redisServer = new RedisServer(redisPort);
        } catch (IOException e) {
            throw new RuntimeException("Failed to start embedded Redis", e);
        }
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() throws IOException {
        redisServer.stop();
    }

}
