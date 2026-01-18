package com.stockbit.qa.api.companies.data;

import com.stockbit.qa.core.models.ApiResponse;
import com.stockbit.qa.models.Company;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Data class for Companies API.
 * Stores request parameters and response data for Companies API calls.
 */
@Data
@Component
public class CompaniesData {

    // Request Parameters
    private Integer quantity;
    private String locale;

    // Response Data
    private ApiResponse<Company> findCompaniesResponse;

    /**
     * Resets all data to default values.
     */
    public void reset() {
        this.quantity = null;
        this.locale = null;
        this.findCompaniesResponse = null;
    }
}

