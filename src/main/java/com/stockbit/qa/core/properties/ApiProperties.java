package com.stockbit.qa.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Properties class for API configuration.
 * Maps to 'api.fakerapi.*' properties in application.properties.
 */
@Data
@Component
@ConfigurationProperties(prefix = "api.fakerapi")
public class ApiProperties {

    private String baseUrl;
    private String defaultLocale;
    private Integer defaultQuantity;
    private String contentType = "application/json";
    private String accept = "application/json";
    private boolean logRequests = true;
    private boolean logResponses = true;
}

