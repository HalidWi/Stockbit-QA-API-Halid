package com.stockbit.qa.core.data;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Data class for storing interceptor-related data.
 * Contains common parameters used across all API requests.
 */
@Data
@Component
public class InterceptorData {

    private String locale;
    private Integer quantity;
    private String requestId;

    /**
     * Resets all interceptor data to default values.
     */
    public void reset() {
        this.locale = null;
        this.quantity = null;
        this.requestId = null;
    }
}
