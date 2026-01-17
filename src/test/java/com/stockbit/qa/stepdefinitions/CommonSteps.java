package com.stockbit.qa.stepdefinitions;

import com.stockbit.qa.client.ApiClient;
import com.stockbit.qa.config.ApiConfig;
import com.stockbit.qa.context.TestContext;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Common step definitions shared across all feature files.
 */
public class CommonSteps {
    
    private final TestContext testContext;
    
    public CommonSteps(TestContext testContext) {
        this.testContext = testContext;
    }
    
    @Before
    public void setup() {
        RestAssured.baseURI = ApiConfig.BASE_URL;
        testContext.clearContext();
    }
    
    @Given("the FakerAPI is available")
    public void theFakerApiIsAvailable() {
        // Health check - verify API is responsive
        ApiClient client = new ApiClient();
        var response = client.get(ApiConfig.PERSONS_ENDPOINT + "?_quantity=1");
        assertThat(response.getStatusCode())
                .as("FakerAPI should be available")
                .isEqualTo(200);
    }
    
    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        assertThat(testContext.getResponse().getStatusCode())
                .as("Expected status code to be %d", expectedStatusCode)
                .isEqualTo(expectedStatusCode);
    }
    
    @Then("the response should contain {string} with value {string}")
    public void theResponseShouldContainWithValue(String field, String expectedValue) {
        String actualValue = testContext.getResponse().jsonPath().getString(field);
        assertThat(actualValue)
                .as("Expected %s to be %s", field, expectedValue)
                .isEqualTo(expectedValue);
    }
}

