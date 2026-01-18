package com.stockbit.qa.api.persons.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.stockbit.qa.api.persons.data.PersonsData;
import com.stockbit.qa.core.json.JsonApi;
import com.stockbit.qa.core.models.ApiResponse;
import com.stockbit.qa.core.properties.EndpointProperties;
import com.stockbit.qa.core.restassured.ResponseApi;
import com.stockbit.qa.core.restassured.ServiceApi;
import com.stockbit.qa.models.Person;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller for Persons API.
 * Handles all REST API calls related to persons.
 */
@Component
public class PersonsController extends ServiceApi {

    @Autowired
    private JsonApi jsonApi;

    @Autowired
    private PersonsData personsData;

    @Autowired
    private EndpointProperties endpointProperties;

    /**
     * Get persons with configured parameters.
     *
     * @return ResponseApi with ApiResponse containing list of Person
     */
    public ResponseApi<ApiResponse<Person>> getPersons() {
        RequestSpecification requestSpec = service("fakerapi");

        // Override quantity if set in personsData
        if (personsData.getQuantity() != null) {
            requestSpec.queryParam("_quantity", personsData.getQuantity());
        }

        // Override locale if set in personsData
        if (personsData.getLocale() != null && !personsData.getLocale().isEmpty()) {
            requestSpec.queryParam("_locale", personsData.getLocale());
        }

        // Add gender filter if set
        if (personsData.getGender() != null && !personsData.getGender().isEmpty()) {
            requestSpec.queryParam("_gender", personsData.getGender());
        }

        Response response = requestSpec.get(endpointProperties.getPersons());
        response.getBody().prettyPrint();

        return jsonApi.fromJson(response, new TypeReference<ApiResponse<Person>>() {});
    }
}

