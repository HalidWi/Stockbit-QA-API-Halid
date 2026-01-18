package com.stockbit.qa.api.products.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.stockbit.qa.api.products.data.ProductsData;
import com.stockbit.qa.core.json.JsonApi;
import com.stockbit.qa.core.models.ApiResponse;
import com.stockbit.qa.core.properties.EndpointProperties;
import com.stockbit.qa.core.restassured.ResponseApi;
import com.stockbit.qa.core.restassured.ServiceApi;
import com.stockbit.qa.models.Product;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller for Products API.
 * Handles all REST API calls related to products.
 */
@Component
public class ProductsController extends ServiceApi {

    @Autowired
    private JsonApi jsonApi;

    @Autowired
    private ProductsData productsData;

    @Autowired
    private EndpointProperties endpointProperties;

    /**
     * Get products with configured parameters.
     *
     * @return ResponseApi with ApiResponse containing list of Product
     */
    public ResponseApi<ApiResponse<Product>> getProducts() {
        RequestSpecification requestSpec = service("fakerapi");

        // Override quantity if set in productsData
        if (productsData.getQuantity() != null) {
            requestSpec.queryParam("_quantity", productsData.getQuantity());
        }

        // Override locale if set in productsData
        if (productsData.getLocale() != null && !productsData.getLocale().isEmpty()) {
            requestSpec.queryParam("_locale", productsData.getLocale());
        }

        // Add price filters if set
        if (productsData.getPriceMin() != null && !productsData.getPriceMin().isEmpty()) {
            requestSpec.queryParam("_price_min", productsData.getPriceMin());
        }

        if (productsData.getPriceMax() != null && !productsData.getPriceMax().isEmpty()) {
            requestSpec.queryParam("_price_max", productsData.getPriceMax());
        }

        // Add taxes type if set
        if (productsData.getTaxesType() != null && !productsData.getTaxesType().isEmpty()) {
            requestSpec.queryParam("_taxes", productsData.getTaxesType());
        }

        Response response = requestSpec.get(endpointProperties.getProducts());
        response.getBody().prettyPrint();

        return jsonApi.fromJson(response, new TypeReference<ApiResponse<Product>>() {});
    }
}

