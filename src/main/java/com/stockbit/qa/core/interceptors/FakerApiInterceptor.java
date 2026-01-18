package com.stockbit.qa.core.interceptors;

import com.stockbit.qa.core.data.InterceptorData;
import com.stockbit.qa.core.properties.ApiProperties;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Interceptor for FakerAPI service calls.
 * Adds common headers and query parameters to all FakerAPI requests.
 */
@Component
public class FakerApiInterceptor implements ServiceInterceptor {

    @Autowired
    private InterceptorData interceptorData;

    @Autowired
    private ApiProperties apiProperties;

    @Override
    public boolean isSupport(String serviceName) {
        return "fakerapi".equalsIgnoreCase(serviceName);
    }

    @Override
    public void prepare(RequestSpecification requestSpecification) {
        // Set default values if not provided
        if (interceptorData.getLocale() == null) {
            interceptorData.setLocale(apiProperties.getDefaultLocale());
        }
        if (interceptorData.getQuantity() == null) {
            interceptorData.setQuantity(apiProperties.getDefaultQuantity());
        }

        // Configure request specification
        requestSpecification
                .header("Content-Type", apiProperties.getContentType())
                .header("Accept", apiProperties.getAccept());

        // Add logging if enabled
        if (apiProperties.isLogRequests()) {
            requestSpecification.log().all();
        }

        // Add default query parameters if set
        if (interceptorData.getQuantity() != null) {
            requestSpecification.queryParam("_quantity", interceptorData.getQuantity());
        }
        if (interceptorData.getLocale() != null && !interceptorData.getLocale().isEmpty()) {
            requestSpecification.queryParam("_locale", interceptorData.getLocale());
        }
    }
}

