package com.stockbit.qa.api.products.data;

import com.stockbit.qa.core.models.ApiResponse;
import com.stockbit.qa.models.Product;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Data class for Products API.
 * Stores request parameters and response data for Products API calls.
 */
@Data
@Component
public class ProductsData {

    // Request Parameters
    private Integer quantity;
    private String locale;
    private String priceMin;
    private String priceMax;
    private String taxesType;

    // Response Data
    private ApiResponse<Product> findProductsResponse;

    /**
     * Resets all data to default values.
     */
    public void reset() {
        this.quantity = null;
        this.locale = null;
        this.priceMin = null;
        this.priceMax = null;
        this.taxesType = null;
        this.findProductsResponse = null;
    }
}

