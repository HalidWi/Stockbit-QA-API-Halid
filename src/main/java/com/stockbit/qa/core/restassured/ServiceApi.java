package com.stockbit.qa.core.restassured;

import com.stockbit.qa.core.interceptors.ServiceInterceptor;
import com.stockbit.qa.core.properties.ApiProperties;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Base class for API service controllers.
 * Provides common functionality for making REST API calls with interceptor support.
 */
public abstract class ServiceApi {

    @Autowired
    private ApiProperties apiProperties;

    @Autowired
    private List<ServiceInterceptor> interceptors;

    /**
     * Creates a new request specification for the specified service.
     * Applies all matching interceptors to prepare the request.
     *
     * @param serviceName The name of the service (used to match interceptors)
     * @return Configured RequestSpecification
     */
    protected RequestSpecification service(String serviceName) {
        RestAssured.baseURI = apiProperties.getBaseUrl();
        
        RequestSpecification requestSpec = RestAssured.given();

        // Apply matching interceptors
        for (ServiceInterceptor interceptor : interceptors) {
            if (interceptor.isSupport(serviceName)) {
                interceptor.prepare(requestSpec);
            }
        }

        return requestSpec;
    }

    /**
     * Gets the API properties.
     *
     * @return ApiProperties instance
     */
    protected ApiProperties getApiProperties() {
        return apiProperties;
    }
}

