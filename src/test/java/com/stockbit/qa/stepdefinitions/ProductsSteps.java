package com.stockbit.qa.stepdefinitions;

import com.stockbit.qa.api.products.data.ProductsData;
import com.stockbit.qa.api.products.services.ProductsController;
import com.stockbit.qa.core.data.InterceptorData;
import com.stockbit.qa.models.Product;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Products API feature.
 * Uses Spring DI for controller and data management.
 */
public class ProductsSteps {

    @Autowired
    private ProductsData productsData;

    @Autowired
    private ProductsController productsController;

    @Autowired
    private InterceptorData interceptorData;

    // ================== Set Request Parameters ==================

    @Given("[products] prepare request set quantity to {int}")
    public void prepareRequestSetQuantityTo(int quantity) {
        productsData.setQuantity(quantity);
    }

    @Given("[products] prepare request set locale to {string}")
    public void prepareRequestSetLocaleTo(String locale) {
        productsData.setLocale(locale);
    }

    @Given("[products] prepare request set price min to {string}")
    public void prepareRequestSetPriceMinTo(String priceMin) {
        productsData.setPriceMin(priceMin);
    }

    @Given("[products] prepare request set price max to {string}")
    public void prepareRequestSetPriceMaxTo(String priceMax) {
        productsData.setPriceMax(priceMax);
    }

    // ================== Send Requests ==================

    @When("I request products with quantity {int} and locale {string}")
    public void iRequestProductsWithQuantityAndLocale(int quantity, String locale) {
        productsData.setQuantity(quantity);
        productsData.setLocale(locale);
        productsData.setFindProductsResponse(productsController.getProducts().getResponseBody());
    }

    @When("[products] send get products request")
    public void sendGetProductsRequest() {
        productsData.setFindProductsResponse(productsController.getProducts().getResponseBody());
    }

    // ================== Assertions ==================

    @Then("the response should contain exactly {int} products")
    public void theResponseShouldContainExactlyProducts(int expectedCount) {
        assertThat(productsData.getFindProductsResponse().getData())
                .as("Expected exactly %d products", expectedCount)
                .hasSize(expectedCount);
    }

    @Then("[products] response should be success")
    public void responseProductsShouldBeSuccess() {
        assertThat(productsData.getFindProductsResponse().isSuccess())
                .as("Response should be successful")
                .isTrue();
    }

    @Then("[products] response should contain {int} products")
    public void responseShouldContainProducts(int expectedCount) {
        assertThat(productsData.getFindProductsResponse().getData())
                .as("Expected exactly %d products", expectedCount)
                .hasSize(expectedCount);
    }

    @And("each product should have the following required fields:")
    public void eachProductShouldHaveTheFollowingRequiredFields(DataTable dataTable) {
        List<String> requiredFields = dataTable.asList().stream()
                .filter(f -> !f.equals("field"))
                .toList();

        List<Product> products = productsData.getFindProductsResponse().getData();

        for (Product product : products) {
            for (String field : requiredFields) {
                switch (field) {
                    case "id" -> assertThat(product.getId()).as("Product should have id").isNotNull();
                    case "name" -> assertThat(product.getName()).as("Product should have name").isNotNull();
                    case "description" -> assertThat(product.getDescription()).as("Product should have description").isNotNull();
                    case "ean" -> assertThat(product.getEan()).as("Product should have ean").isNotNull();
                    case "upc" -> assertThat(product.getUpc()).as("Product should have upc").isNotNull();
                    case "image" -> assertThat(product.getImage()).as("Product should have image").isNotNull();
                    case "price" -> assertThat(product.getPrice()).as("Product should have price").isNotNull();
                    case "net_price" -> assertThat(product.getNet_price()).as("Product should have net_price").isNotNull();
                }
            }
        }
    }

    @And("all products should have positive prices")
    public void allProductsShouldHavePositivePrices() {
        List<Product> products = productsData.getFindProductsResponse().getData();

        for (Product product : products) {
            double price = product.getPrice();
            assertThat(price)
                    .as("Price should be positive")
                    .isGreaterThan(0);
        }
    }

    @And("the product prices should be consistent with taxes")
    public void theProductPricesShouldBeConsistentWithTaxes() {
        List<Product> products = productsData.getFindProductsResponse().getData();

        for (Product product : products) {
            double price = product.getPrice();
            double netPrice = product.getNet_price();

            // Price should be greater than or equal to net price (due to taxes)
            assertThat(price)
                    .as("Price should be >= net price")
                    .isGreaterThanOrEqualTo(netPrice);
        }
    }
}
