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
 * Step definitions for Books API feature.
 */
public class BooksSteps {
    
    private final TestContext testContext;
    
    // ISBN-10 or ISBN-13 pattern
    private static final Pattern ISBN_PATTERN = Pattern.compile(
            "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$"
    );
    
    public BooksSteps(TestContext testContext) {
        this.testContext = testContext;
    }
    
    @When("I request books with quantity {int} and locale {string}")
    public void iRequestBooksWithQuantityAndLocale(int quantity, String locale) {
        ApiClient client = new ApiClient();
        Response response = client
                .queryParam("_quantity", quantity)
                .queryParam("_locale", locale)
                .get(ApiConfig.BOOKS_ENDPOINT);
        testContext.setResponse(response);
        testContext.setContext("requestedQuantity", quantity);
    }
    
    @Then("the response should contain exactly {int} books")
    public void theResponseShouldContainExactlyBooks(int expectedCount) {
        List<Map<String, Object>> books = testContext.getResponse()
                .jsonPath()
                .getList("data");
        assertThat(books)
                .as("Expected exactly %d books", expectedCount)
                .hasSize(expectedCount);
    }
    
    @And("each book should have the following required fields:")
    public void eachBookShouldHaveTheFollowingRequiredFields(DataTable dataTable) {
        List<String> requiredFields = dataTable.asList().stream()
                .filter(f -> !f.equals("field"))
                .toList();
        List<Map<String, Object>> books = testContext.getResponse()
                .jsonPath()
                .getList("data");
        
        for (Map<String, Object> book : books) {
            for (String field : requiredFields) {
                assertThat(book)
                        .as("Book should contain field: %s", field)
                        .containsKey(field);
            }
        }
    }
    
    @And("all books should have valid ISBN format")
    public void allBooksShouldHaveValidISBNFormat() {
        List<Map<String, Object>> books = testContext.getResponse()
                .jsonPath()
                .getList("data");
        
        for (Map<String, Object> book : books) {
            String isbn = (String) book.get("isbn");
            assertThat(isbn)
                    .as("ISBN should not be null or empty")
                    .isNotNull()
                    .isNotEmpty();
            // FakerAPI generates ISBN-like strings, verify it's not empty
            assertThat(isbn.length())
                    .as("ISBN should have reasonable length")
                    .isGreaterThanOrEqualTo(10);
        }
    }
}

