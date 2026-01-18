package com.stockbit.qa.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Properties class for request configuration.
 * Maps to 'api.request.*' properties in application.properties.
 */
@Data
@Component
@ConfigurationProperties(prefix = "api.request")
public class RequestProperties {

    private int connectionTimeout = 10000;
    private int readTimeout = 10000;
    private String contentType = "application/json";
    private String accept = "application/json";
}

