package com.stockbit.qa.stepdefinitions;

import com.stockbit.qa.api.companies.data.CompaniesData;
import com.stockbit.qa.api.companies.services.CompaniesController;
import com.stockbit.qa.core.data.InterceptorData;
import com.stockbit.qa.models.Company;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Companies API feature.
 * Uses Spring DI for controller and data management.
 */
public class CompaniesSteps {

    @Autowired
    private CompaniesData companiesData;

    @Autowired
    private CompaniesController companiesController;

    @Autowired
    private InterceptorData interceptorData;

    // ================== Set Request Parameters ==================

    @Given("[companies] prepare request set quantity to {int}")
    public void prepareRequestSetQuantityTo(int quantity) {
        companiesData.setQuantity(quantity);
    }

    @Given("[companies] prepare request set locale to {string}")
    public void prepareRequestSetLocaleTo(String locale) {
        companiesData.setLocale(locale);
    }

    // ================== Send Requests ==================

    @When("I request companies with quantity {int} and locale {string}")
    public void iRequestCompaniesWithQuantityAndLocale(int quantity, String locale) {
        companiesData.setQuantity(quantity);
        companiesData.setLocale(locale);
        companiesData.setFindCompaniesResponse(companiesController.getCompanies().getResponseBody());
    }

    @When("[companies] send get companies request")
    public void sendGetCompaniesRequest() {
        companiesData.setFindCompaniesResponse(companiesController.getCompanies().getResponseBody());
    }

    // ================== Assertions ==================

    @Then("the response should contain exactly {int} companies")
    public void theResponseShouldContainExactlyCompanies(int expectedCount) {
        assertThat(companiesData.getFindCompaniesResponse().getData())
                .as("Expected exactly %d companies", expectedCount)
                .hasSize(expectedCount);
    }

    @Then("[companies] response should be success")
    public void responseCompaniesShouldBeSuccess() {
        assertThat(companiesData.getFindCompaniesResponse().isSuccess())
                .as("Response should be successful")
                .isTrue();
    }

    @Then("[companies] response should contain {int} companies")
    public void responseShouldContainCompanies(int expectedCount) {
        assertThat(companiesData.getFindCompaniesResponse().getData())
                .as("Expected exactly %d companies", expectedCount)
                .hasSize(expectedCount);
    }

    @And("each company should have the following required fields:")
    public void eachCompanyShouldHaveTheFollowingRequiredFields(DataTable dataTable) {
        List<String> requiredFields = dataTable.asList().stream()
                .filter(f -> !f.equals("field"))
                .toList();

        List<Company> companies = companiesData.getFindCompaniesResponse().getData();

        for (Company company : companies) {
            for (String field : requiredFields) {
                switch (field) {
                    case "id" -> assertThat(company.getId()).as("Company should have id").isNotNull();
                    case "name" -> assertThat(company.getName()).as("Company should have name").isNotNull();
                    case "email" -> assertThat(company.getEmail()).as("Company should have email").isNotNull();
                    case "vat" -> assertThat(company.getVat()).as("Company should have vat").isNotNull();
                    case "phone" -> assertThat(company.getPhone()).as("Company should have phone").isNotNull();
                    case "country" -> assertThat(company.getCountry()).as("Company should have country").isNotNull();
                    case "website" -> assertThat(company.getWebsite()).as("Company should have website").isNotNull();
                    case "contact" -> assertThat(company.getContact()).as("Company should have contact").isNotNull();
                }
            }
        }
    }

    @And("each company should have contact information")
    public void eachCompanyShouldHaveContactInformation() {
        List<Company> companies = companiesData.getFindCompaniesResponse().getData();

        for (Company company : companies) {
            assertThat(company.getContact())
                    .as("Company should have contact field")
                    .isNotNull();
        }
    }
}
