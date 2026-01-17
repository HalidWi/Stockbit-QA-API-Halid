package com.stockbit.qa.client;

import com.stockbit.qa.config.ApiConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

/**
 * API Client class implementing the Builder pattern for making HTTP requests.
 * Provides a fluent interface for API interactions.
 */
public class ApiClient {
    
    private final RequestSpecification requestSpec;
    
    public ApiClient() {
        RestAssured.baseURI = ApiConfig.BASE_URL;
        this.requestSpec = RestAssured.given()
                .contentType("application/json")
                .accept("application/json");
    }
    
    /**
     * Add query parameter to the request.
     * @param key Parameter name
     * @param value Parameter value
     * @return ApiClient instance for chaining
     */
    public ApiClient queryParam(String key, Object value) {
        this.requestSpec.queryParam(key, value);
        return this;
    }
    
    /**
     * Add multiple query parameters to the request.
     * @param params Map of parameters
     * @return ApiClient instance for chaining
     */
    public ApiClient queryParams(Map<String, Object> params) {
        this.requestSpec.queryParams(params);
        return this;
    }
    
    /**
     * Add header to the request.
     * @param key Header name
     * @param value Header value
     * @return ApiClient instance for chaining
     */
    public ApiClient header(String key, String value) {
        this.requestSpec.header(key, value);
        return this;
    }
    
    /**
     * Perform GET request to the specified endpoint.
     * @param endpoint API endpoint
     * @return Response object
     */
    public Response get(String endpoint) {
        return this.requestSpec
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }
    
    /**
     * Perform GET request with default locale.
     * @param endpoint API endpoint
     * @param quantity Number of items to generate
     * @return Response object
     */
    public Response getWithLocale(String endpoint, int quantity) {
        return this.requestSpec
                .queryParam("_locale", ApiConfig.DEFAULT_LOCALE)
                .queryParam("_quantity", quantity)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }
}

