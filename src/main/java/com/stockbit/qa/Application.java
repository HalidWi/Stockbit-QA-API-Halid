package com.stockbit.qa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Main Spring Boot Application class.
 * Entry point for the test framework.
 */
@SpringBootApplication
@EnableConfigurationProperties
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

