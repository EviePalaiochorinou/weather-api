package com.example.configuration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimiterConfig {

    @Bean
    public Bucket requestBucket() {
        Bandwidth limit = Bandwidth.classic(10000, Refill.intervally(10000, Duration.ofDays(1)));
        return Bucket4j.builder().addLimit(limit).build();
    }
}
