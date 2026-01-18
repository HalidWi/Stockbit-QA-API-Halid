package com.stockbit.qa.stepdefinitions;

import com.stockbit.qa.api.persons.data.PersonsData;
import com.stockbit.qa.api.persons.services.PersonsController;
import com.stockbit.qa.core.data.InterceptorData;
import com.stockbit.qa.models.Person;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Persons API feature.
 * Uses Spring DI for controller and data management.
 */
public class PersonsSteps {

    @Autowired
    private PersonsData personsData;

    @Autowired
    private PersonsController personsController;

    @Autowired
    private InterceptorData interceptorData;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    // ================== Set Request Parameters ==================

    @Given("[persons] prepare request set quantity to {int}")
    public void prepareRequestSetQuantityTo(int quantity) {
        personsData.setQuantity(quantity);
    }

    @Given("[persons] prepare request set locale to {string}")
    public void prepareRequestSetLocaleTo(String locale) {
        personsData.setLocale(locale);
    }

    @Given("[persons] prepare request set gender to {string}")
    public void prepareRequestSetGenderTo(String gender) {
        personsData.setGender(gender);
    }

    // ================== Send Requests ==================

    @When("I request persons with quantity {int} and locale {string}")
    public void iRequestPersonsWithQuantityAndLocale(int quantity, String locale) {
        personsData.setQuantity(quantity);
        personsData.setLocale(locale);
        personsData.setFindPersonsResponse(personsController.getPersons().getResponseBody());
    }

    @When("[persons] send get persons request")
    public void sendGetPersonsRequest() {
        personsData.setFindPersonsResponse(personsController.getPersons().getResponseBody());
    }

    // ================== Assertions ==================

    @Then("the response should contain exactly {int} persons")
    public void theResponseShouldContainExactlyPersons(int expectedCount) {
        assertThat(personsData.getFindPersonsResponse().getData())
                .as("Expected exactly %d persons", expectedCount)
                .hasSize(expectedCount);
    }

    @Then("the response should contain {int} or more persons")
    public void theResponseShouldContainOrMorePersons(int minCount) {
        assertThat(personsData.getFindPersonsResponse().getData().size())
                .as("Expected at least %d persons", minCount)
                .isGreaterThanOrEqualTo(minCount);
    }

    @Then("[persons] response should be success")
    public void responsePersonsShouldBeSuccess() {
        assertThat(personsData.getFindPersonsResponse().isSuccess())
                .as("Response should be successful")
                .isTrue();
    }

    @Then("[persons] response should contain {int} persons")
    public void responseShouldContainPersons(int expectedCount) {
        assertThat(personsData.getFindPersonsResponse().getData())
                .as("Expected exactly %d persons", expectedCount)
                .hasSize(expectedCount);
    }

    @And("each person should have the following required fields:")
    public void eachPersonShouldHaveTheFollowingRequiredFields(DataTable dataTable) {
        List<String> requiredFields = dataTable.asList().stream()
                .filter(f -> !f.equals("field"))
                .toList();

        List<Person> persons = personsData.getFindPersonsResponse().getData();

        for (Person person : persons) {
            for (String field : requiredFields) {
                switch (field) {
                    case "id" -> assertThat(person.getId()).as("Person should have id").isNotNull();
                    case "firstname" -> assertThat(person.getFirstname()).as("Person should have firstname").isNotNull();
                    case "lastname" -> assertThat(person.getLastname()).as("Person should have lastname").isNotNull();
                    case "email" -> assertThat(person.getEmail()).as("Person should have email").isNotNull();
                    case "phone" -> assertThat(person.getPhone()).as("Person should have phone").isNotNull();
                    case "birthday" -> assertThat(person.getBirthday()).as("Person should have birthday").isNotNull();
                    case "gender" -> assertThat(person.getGender()).as("Person should have gender").isNotNull();
                    case "address" -> assertThat(person.getAddress()).as("Person should have address").isNotNull();
                }
            }
        }
    }

    @And("all persons should have valid email format")
    public void allPersonsShouldHaveValidEmailFormat() {
        List<Person> persons = personsData.getFindPersonsResponse().getData();

        for (Person person : persons) {
            String email = person.getEmail();
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
        List<Person> persons = personsData.getFindPersonsResponse().getData();

        for (Person person : persons) {
            String gender = person.getGender();
            assertThat(gender)
                    .as("Gender should be either '%s' or '%s'", gender1, gender2)
                    .isIn(gender1, gender2);
        }
    }
}
