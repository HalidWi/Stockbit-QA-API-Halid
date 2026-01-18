package com.stockbit.qa.api.users.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.stockbit.qa.api.users.data.UsersData;
import com.stockbit.qa.core.json.JsonApi;
import com.stockbit.qa.core.models.ApiResponse;
import com.stockbit.qa.core.properties.EndpointProperties;
import com.stockbit.qa.core.restassured.ResponseApi;
import com.stockbit.qa.core.restassured.ServiceApi;
import com.stockbit.qa.models.User;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller for Users API.
 * Handles all REST API calls related to users.
 */
@Component
public class UsersController extends ServiceApi {

    @Autowired
    private JsonApi jsonApi;

    @Autowired
    private UsersData usersData;

    @Autowired
    private EndpointProperties endpointProperties;

    /**
     * Get users with configured parameters.
     *
     * @return ResponseApi with ApiResponse containing list of User
     */
    public ResponseApi<ApiResponse<User>> getUsers() {
        RequestSpecification requestSpec = service("fakerapi");

        // Override quantity if set in usersData
        if (usersData.getQuantity() != null) {
            requestSpec.queryParam("_quantity", usersData.getQuantity());
        }

        // Override locale if set in usersData
        if (usersData.getLocale() != null && !usersData.getLocale().isEmpty()) {
            requestSpec.queryParam("_locale", usersData.getLocale());
        }

        // Add gender filter if set
        if (usersData.getGender() != null && !usersData.getGender().isEmpty()) {
            requestSpec.queryParam("_gender", usersData.getGender());
        }

        Response response = requestSpec.get(endpointProperties.getUsers());
        response.getBody().prettyPrint();

        return jsonApi.fromJson(response, new TypeReference<ApiResponse<User>>() {});
    }
}

