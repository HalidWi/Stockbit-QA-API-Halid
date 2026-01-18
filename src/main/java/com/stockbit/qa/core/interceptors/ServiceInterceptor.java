package com.stockbit.qa.core.interceptors;

import io.restassured.specification.RequestSpecification;

/**
 * Service Interceptor interface for REST API calls.
 * Implementations can add common headers, query parameters, or perform other
 * pre-request operations based on the service name.
 */
public interface ServiceInterceptor {

    /**
     * Determines if this interceptor supports the given service name.
     *
     * @param serviceName The name of the service being called
     * @return true if this interceptor should be applied, false otherwise
     */
    boolean isSupport(String serviceName);

    /**
     * Prepares the request specification by adding headers, parameters, etc.
     *
     * @param requestSpecification The REST Assured request specification to modify
     */
    void prepare(RequestSpecification requestSpecification);
}

