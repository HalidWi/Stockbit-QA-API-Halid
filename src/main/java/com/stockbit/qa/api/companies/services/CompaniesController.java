package com.stockbit.qa.api.companies.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.stockbit.qa.api.companies.data.CompaniesData;
import com.stockbit.qa.core.json.JsonApi;
import com.stockbit.qa.core.models.ApiResponse;
import com.stockbit.qa.core.properties.EndpointProperties;
import com.stockbit.qa.core.restassured.ResponseApi;
import com.stockbit.qa.core.restassured.ServiceApi;
import com.stockbit.qa.models.Company;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller for Companies API.
 * Handles all REST API calls related to companies.
 */
@Component
public class CompaniesController extends ServiceApi {

    @Autowired
    private JsonApi jsonApi;

    @Autowired
    private CompaniesData companiesData;

    @Autowired
    private EndpointProperties endpointProperties;

    /**
     * Get companies with configured parameters.
     *
     * @return ResponseApi with ApiResponse containing list of Company
     */
    public ResponseApi<ApiResponse<Company>> getCompanies() {
        RequestSpecification requestSpec = service("fakerapi");

        // Override quantity if set in companiesData
        if (companiesData.getQuantity() != null) {
            requestSpec.queryParam("_quantity", companiesData.getQuantity());
        }

        // Override locale if set in companiesData
        if (companiesData.getLocale() != null && !companiesData.getLocale().isEmpty()) {
            requestSpec.queryParam("_locale", companiesData.getLocale());
        }

        Response response = requestSpec.get(endpointProperties.getCompanies());
        response.getBody().prettyPrint();

        return jsonApi.fromJson(response, new TypeReference<ApiResponse<Company>>() {});
    }
}

