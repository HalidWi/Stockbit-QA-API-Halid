package com.stockbit.qa.config;

/**
 * Configuration class for API settings.
 * Contains base URL and common configuration parameters.
 */
public class ApiConfig {
    
    public static final String BASE_URL = "https://fakerapi.it/api/v1";
    public static final String DEFAULT_LOCALE = "en_US";
    public static final int DEFAULT_QUANTITY = 5;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 10000;
    
    // API Endpoints
    public static final String PERSONS_ENDPOINT = "/persons";
    public static final String ADDRESSES_ENDPOINT = "/addresses";
    public static final String COMPANIES_ENDPOINT = "/companies";
    public static final String BOOKS_ENDPOINT = "/books";
    public static final String PRODUCTS_ENDPOINT = "/products";
    public static final String USERS_ENDPOINT = "/users";
    public static final String TEXTS_ENDPOINT = "/texts";
    public static final String IMAGES_ENDPOINT = "/images";
    public static final String CREDIT_CARDS_ENDPOINT = "/credit_cards";
    
    private ApiConfig() {
        // Prevent instantiation
    }
}

