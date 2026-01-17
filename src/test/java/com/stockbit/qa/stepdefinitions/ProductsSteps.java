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
 * Step definitions for Products API feature.
 */
public class ProductsSteps {
    
    private final TestContext testContext;
    
    public ProductsSteps(TestContext testContext) {
        this.testContext = testContext;
    }
    
    @When("I request products with quantity {int} and locale {string}")
    public void iRequestProductsWithQuantityAndLocale(int quantity, String locale) {
        ApiClient client = new ApiClient();
        Response response = client
                .queryParam("_quantity", quantity)
                .queryParam("_locale", locale)
                .get(ApiConfig.PRODUCTS_ENDPOINT);
        testContext.setResponse(response);
        testContext.setContext("requestedQuantity", quantity);
    }
    
    @Then("the response should contain exactly {int} products")
    public void theResponseShouldContainExactlyProducts(int expectedCount) {
        List<Map<String, Object>> products = testContext.getResponse()
                .jsonPath()
                .getList("data");
        assertThat(products)
                .as("Expected exactly %d products", expectedCount)
                .hasSize(expectedCount);
    }
    
    @And("each product should have the following required fields:")
    public void eachProductShouldHaveTheFollowingRequiredFields(DataTable dataTable) {
        List<String> requiredFields = dataTable.asList().stream()
                .filter(f -> !f.equals("field"))
                .toList();
        List<Map<String, Object>> products = testContext.getResponse()
                .jsonPath()
                .getList("data");
        
        for (Map<String, Object> product : products) {
            for (String field : requiredFields) {
                assertThat(product)
                        .as("Product should contain field: %s", field)
                        .containsKey(field);
            }
        }
    }
    
    @And("all products should have positive prices")
    public void allProductsShouldHavePositivePrices() {
        List<Map<String, Object>> products = testContext.getResponse()
                .jsonPath()
                .getList("data");
        
        for (Map<String, Object> product : products) {
            Object priceObj = product.get("price");
            double price = priceObj instanceof Number ? ((Number) priceObj).doubleValue() : 0.0;
            assertThat(price)
                    .as("Price should be positive")
                    .isGreaterThan(0);
        }
    }
    
    @And("the product prices should be consistent with taxes")
    public void theProductPricesShouldBeConsistentWithTaxes() {
        List<Map<String, Object>> products = testContext.getResponse()
                .jsonPath()
                .getList("data");
        
        for (Map<String, Object> product : products) {
            Object priceObj = product.get("price");
            Object netPriceObj = product.get("net_price");
            
            if (priceObj != null && netPriceObj != null) {
                double price = ((Number) priceObj).doubleValue();
                double netPrice = ((Number) netPriceObj).doubleValue();
                
                // Price should be greater than or equal to net price (due to taxes)
                assertThat(price)
                        .as("Price should be >= net price")
                        .isGreaterThanOrEqualTo(netPrice);
            }
        }
    }
}

