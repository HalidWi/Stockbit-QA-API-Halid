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
 * Step definitions for Persons API feature.
 */
public class PersonsSteps {
    
    private final TestContext testContext;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );
    
    public PersonsSteps(TestContext testContext) {
        this.testContext = testContext;
    }
    
    @When("I request persons with quantity {int} and locale {string}")
    public void iRequestPersonsWithQuantityAndLocale(int quantity, String locale) {
        ApiClient client = new ApiClient();
        Response response = client
                .queryParam("_quantity", quantity)
                .queryParam("_locale", locale)
                .get(ApiConfig.PERSONS_ENDPOINT);
        testContext.setResponse(response);
        testContext.setContext("requestedQuantity", quantity);
    }
    
    @Then("the response should contain exactly {int} persons")
    public void theResponseShouldContainExactlyPersons(int expectedCount) {
        List<Map<String, Object>> persons = testContext.getResponse()
                .jsonPath()
                .getList("data");
        assertThat(persons)
                .as("Expected exactly %d persons", expectedCount)
                .hasSize(expectedCount);
    }
    
    @Then("the response should contain {int} or more persons")
    public void theResponseShouldContainOrMorePersons(int minCount) {
        List<Map<String, Object>> persons = testContext.getResponse()
                .jsonPath()
                .getList("data");
        assertThat(persons.size())
                .as("Expected at least %d persons", minCount)
                .isGreaterThanOrEqualTo(minCount);
    }
    
    @And("each person should have the following required fields:")
    public void eachPersonShouldHaveTheFollowingRequiredFields(DataTable dataTable) {
        List<String> requiredFields = dataTable.asList().stream()
                .filter(f -> !f.equals("field"))
                .toList();
        List<Map<String, Object>> persons = testContext.getResponse()
                .jsonPath()
                .getList("data");
        
        for (Map<String, Object> person : persons) {
            for (String field : requiredFields) {
                assertThat(person)
                        .as("Person should contain field: %s", field)
                        .containsKey(field);
                assertThat(person.get(field))
                        .as("Field %s should not be null", field)
                        .isNotNull();
            }
        }
    }
    
    @And("all persons should have valid email format")
    public void allPersonsShouldHaveValidEmailFormat() {
        List<Map<String, Object>> persons = testContext.getResponse()
                .jsonPath()
                .getList("data");
        
        for (Map<String, Object> person : persons) {
            String email = (String) person.get("email");
            assertThat(email)
                    .as("Email should not be null")
                    .isNotNull();
            assertThat(EMAIL_PATTERN.matcher(email).matches())
                    .as("Email '%s' should have valid format", email)
                    .isTrue();
        }
    }
    
    @And("all persons should have gender either {string} or {string}")
    public void allPersonsShouldHaveGenderEither(String gender1, String gender2) {
        List<Map<String, Object>> persons = testContext.getResponse()
                .jsonPath()
                .getList("data");
        
        for (Map<String, Object> person : persons) {
            String gender = (String) person.get("gender");
            assertThat(gender)
                    .as("Gender should be either '%s' or '%s'", gender1, gender2)
                    .isIn(gender1, gender2);
        }
    }
}

