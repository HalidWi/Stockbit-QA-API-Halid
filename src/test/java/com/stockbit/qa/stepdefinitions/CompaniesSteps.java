package com.stockbit.qa.stepdefinitions;

import com.stockbit.qa.client.ApiClient;
import com.stockbit.qa.config.ApiConfig;
import com.stockbit.qa.context.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Companies API feature.
 */
public class CompaniesSteps {
    
    private final TestContext testContext;
    
    public CompaniesSteps(TestContext testContext) {
        this.testContext = testContext;
    }
    
    @When("I request companies with quantity {int} and locale {string}")
    public void iRequestCompaniesWithQuantityAndLocale(int quantity, String locale) {
        ApiClient client = new ApiClient();
        Response response = client
                .queryParam("_quantity", quantity)
                .queryParam("_locale", locale)
                .get(ApiConfig.COMPANIES_ENDPOINT);
        testContext.setResponse(response);
        testContext.setContext("requestedQuantity", quantity);
    }
    
    @Then("the response should contain exactly {int} companies")
    public void theResponseShouldContainExactlyCompanies(int expectedCount) {
        List<Map<String, Object>> companies = testContext.getResponse()
                .jsonPath()
                .getList("data");
        assertThat(companies)
                .as("Expected exactly %d companies", expectedCount)
                .hasSize(expectedCount);
    }
    
    @And("each company should have the following required fields:")
    public void eachCompanyShouldHaveTheFollowingRequiredFields(DataTable dataTable) {
        List<String> requiredFields = dataTable.asList().stream()
                .filter(f -> !f.equals("field"))
                .toList();
        List<Map<String, Object>> companies = testContext.getResponse()
                .jsonPath()
                .getList("data");
        
        for (Map<String, Object> company : companies) {
            for (String field : requiredFields) {
                assertThat(company)
                        .as("Company should contain field: %s", field)
                        .containsKey(field);
            }
        }
    }
    
    @And("each company should have contact information")
    public void eachCompanyShouldHaveContactInformation() {
        List<Map<String, Object>> companies = testContext.getResponse()
                .jsonPath()
                .getList("data");
        
        for (Map<String, Object> company : companies) {
            assertThat(company)
                    .as("Company should have contact field")
                    .containsKey("contact");
        }
    }
}

