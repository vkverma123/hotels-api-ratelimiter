package com.hotels.web.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({ "defaultRateLimitConfig.properties", "customRateLimitConfig.properties" })
public class HotelsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelsServiceApplication.class, args);
    }
}
