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
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Users API feature.
 */
public class UsersSteps {
    
    private final TestContext testContext;
    
    // UUID v4 pattern
    private static final Pattern UUID_PATTERN = Pattern.compile(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"
    );
    
    public UsersSteps(TestContext testContext) {
        this.testContext = testContext;
    }
    
    @When("I request users with quantity {int} and locale {string}")
    public void iRequestUsersWithQuantityAndLocale(int quantity, String locale) {
        ApiClient client = new ApiClient();
        Response response = client
                .queryParam("_quantity", quantity)
                .queryParam("_locale", locale)
                .get(ApiConfig.USERS_ENDPOINT);
        testContext.setResponse(response);
        testContext.setContext("requestedQuantity", quantity);
    }
    
    @Then("the response should contain exactly {int} users")
    public void theResponseShouldContainExactlyUsers(int expectedCount) {
        List<Map<String, Object>> users = testContext.getResponse()
                .jsonPath()
                .getList("data");
        assertThat(users)
                .as("Expected exactly %d users", expectedCount)
                .hasSize(expectedCount);
    }
    
    @And("each user should have the following required fields:")
    public void eachUserShouldHaveTheFollowingRequiredFields(DataTable dataTable) {
        List<String> requiredFields = dataTable.asList().stream()
                .filter(f -> !f.equals("field"))
                .toList();
        List<Map<String, Object>> users = testContext.getResponse()
                .jsonPath()
                .getList("data");
        
        for (Map<String, Object> user : users) {
            for (String field : requiredFields) {
                assertThat(user)
                        .as("User should contain field: %s", field)
                        .containsKey(field);
            }
        }
    }
    
    @And("all users should have valid UUID format")
    public void allUsersShouldHaveValidUUIDFormat() {
        List<Map<String, Object>> users = testContext.getResponse()
                .jsonPath()
                .getList("data");
        
        for (Map<String, Object> user : users) {
            String uuid = (String) user.get("uuid");
            assertThat(uuid)
                    .as("UUID should not be null")
                    .isNotNull();
            assertThat(UUID_PATTERN.matcher(uuid).matches())
                    .as("UUID '%s' should have valid format", uuid)
                    .isTrue();
        }
    }
    
    @And("all users should have non-empty passwords")
    public void allUsersShouldHaveNonEmptyPasswords() {
        List<Map<String, Object>> users = testContext.getResponse()
                .jsonPath()
                .getList("data");
        
        for (Map<String, Object> user : users) {
            String password = (String) user.get("password");
            assertThat(password)
                    .as("Password should not be null or empty")
                    .isNotNull()
                    .isNotEmpty();
        }
    }
}

