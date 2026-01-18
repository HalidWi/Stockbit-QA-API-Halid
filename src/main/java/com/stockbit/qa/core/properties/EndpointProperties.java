package com.stockbit.qa.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Properties class for API endpoints configuration.
 * Maps to 'api.endpoints.*' properties in application.properties.
 */
@Data
@Component
@ConfigurationProperties(prefix = "api.endpoints")
public class EndpointProperties {

    private String persons;
    private String companies;
    private String books;
    private String products;
    private String users;
    private String addresses;
    private String texts;
    private String images;
    private String creditCards;
}

