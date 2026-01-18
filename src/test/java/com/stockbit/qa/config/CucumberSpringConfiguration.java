package com.stockbit.qa.config;

import com.stockbit.qa.Application;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Cucumber Spring Configuration.
 * Integrates Cucumber with Spring Boot test context.
 */
@CucumberContextConfiguration
@SpringBootTest(classes = Application.class)
public class CucumberSpringConfiguration {
    // This class serves as the Cucumber-Spring integration configuration
}

