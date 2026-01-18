package com.stockbit.qa.stepdefinitions;

import com.stockbit.qa.api.books.data.BooksData;
import com.stockbit.qa.api.companies.data.CompaniesData;
import com.stockbit.qa.api.persons.data.PersonsData;
import com.stockbit.qa.api.persons.services.PersonsController;
import com.stockbit.qa.api.products.data.ProductsData;
import com.stockbit.qa.api.users.data.UsersData;
import com.stockbit.qa.core.data.InterceptorData;
import com.stockbit.qa.core.models.ApiResponse;
import com.stockbit.qa.core.properties.ApiProperties;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Common step definitions shared across all feature files.
 * Uses Spring DI for configuration and data management.
 */
public class CommonSteps {

    @Autowired
    private ApiProperties apiProperties;

    @Autowired
    private InterceptorData interceptorData;

    @Autowired
    private PersonsData personsData;

    @Autowired
    private CompaniesData companiesData;

    @Autowired
    private BooksData booksData;

    @Autowired
    private ProductsData productsData;

    @Autowired
    private UsersData usersData;

    @Autowired
    private PersonsController personsController;

    @Before
    public void setup() {
        // Reset all data before each scenario
        interceptorData.reset();
        personsData.reset();
        companiesData.reset();
        booksData.reset();
        productsData.reset();
        usersData.reset();
    }

    @Given("the FakerAPI is available")
    public void theFakerApiIsAvailable() {
        // Health check - verify API is responsive
        personsData.setQuantity(1);
        var response = personsController.getPersons();
        assertThat(response.getStatusCode())
                .as("FakerAPI should be available")
                .isEqualTo(200);
        // Reset after health check
        personsData.reset();
    }

    @Given("[common] set default locale to {string}")
    public void setDefaultLocaleTo(String locale) {
        interceptorData.setLocale(locale);
    }

    @Given("[common] set default quantity to {int}")
    public void setDefaultQuantityTo(int quantity) {
        interceptorData.setQuantity(quantity);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        // Get the current response from any of the data classes that has a response
        ApiResponse<?> response = getCurrentResponse();
        
        assertThat(response)
                .as("Response should not be null")
                .isNotNull();
        
        assertThat(response.getCode())
                .as("Expected status code to be %d", expectedStatusCode)
                .isEqualTo(expectedStatusCode);
    }

    @Then("the response should contain {string} with value {string}")
    public void theResponseShouldContainWithValue(String field, String expectedValue) {
        ApiResponse<?> response = getCurrentResponse();
        
        assertThat(response)
                .as("Response should not be null")
                .isNotNull();

        switch (field) {
            case "status" -> assertThat(response.getStatus())
                    .as("Expected %s to be %s", field, expectedValue)
                    .isEqualTo(expectedValue);
            case "locale" -> assertThat(response.getLocale())
                    .as("Expected %s to be %s", field, expectedValue)
                    .isEqualTo(expectedValue);
            default -> throw new IllegalArgumentException("Unknown field: " + field);
        }
    }

    /**
     * Gets the current response from whichever API was called.
     * Checks all data classes and returns the one that has a response.
     */
    private ApiResponse<?> getCurrentResponse() {
        if (personsData.getFindPersonsResponse() != null) {
            return personsData.getFindPersonsResponse();
        }
        if (companiesData.getFindCompaniesResponse() != null) {
            return companiesData.getFindCompaniesResponse();
        }
        if (booksData.getFindBooksResponse() != null) {
            return booksData.getFindBooksResponse();
        }
        if (productsData.getFindProductsResponse() != null) {
            return productsData.getFindProductsResponse();
        }
        if (usersData.getFindUsersResponse() != null) {
            return usersData.getFindUsersResponse();
        }
        return null;
    }
}
