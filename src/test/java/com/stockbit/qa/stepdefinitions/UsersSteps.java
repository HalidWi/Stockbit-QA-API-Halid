package com.stockbit.qa.stepdefinitions;

import com.stockbit.qa.api.users.data.UsersData;
import com.stockbit.qa.api.users.services.UsersController;
import com.stockbit.qa.core.data.InterceptorData;
import com.stockbit.qa.models.User;
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
 * Step definitions for Users API feature.
 * Uses Spring DI for controller and data management.
 */
public class UsersSteps {

    @Autowired
    private UsersData usersData;

    @Autowired
    private UsersController usersController;

    @Autowired
    private InterceptorData interceptorData;

    // UUID v4 pattern
    private static final Pattern UUID_PATTERN = Pattern.compile(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"
    );

    // ================== Set Request Parameters ==================

    @Given("[users] prepare request set quantity to {int}")
    public void prepareRequestSetQuantityTo(int quantity) {
        usersData.setQuantity(quantity);
    }

    @Given("[users] prepare request set locale to {string}")
    public void prepareRequestSetLocaleTo(String locale) {
        usersData.setLocale(locale);
    }

    @Given("[users] prepare request set gender to {string}")
    public void prepareRequestSetGenderTo(String gender) {
        usersData.setGender(gender);
    }

    // ================== Send Requests ==================

    @When("I request users with quantity {int} and locale {string}")
    public void iRequestUsersWithQuantityAndLocale(int quantity, String locale) {
        usersData.setQuantity(quantity);
        usersData.setLocale(locale);
        usersData.setFindUsersResponse(usersController.getUsers().getResponseBody());
    }

    @When("[users] send get users request")
    public void sendGetUsersRequest() {
        usersData.setFindUsersResponse(usersController.getUsers().getResponseBody());
    }

    // ================== Assertions ==================

    @Then("the response should contain exactly {int} users")
    public void theResponseShouldContainExactlyUsers(int expectedCount) {
        assertThat(usersData.getFindUsersResponse().getData())
                .as("Expected exactly %d users", expectedCount)
                .hasSize(expectedCount);
    }

    @Then("[users] response should be success")
    public void responseUsersShouldBeSuccess() {
        assertThat(usersData.getFindUsersResponse().isSuccess())
                .as("Response should be successful")
                .isTrue();
    }

    @Then("[users] response should contain {int} users")
    public void responseShouldContainUsers(int expectedCount) {
        assertThat(usersData.getFindUsersResponse().getData())
                .as("Expected exactly %d users", expectedCount)
                .hasSize(expectedCount);
    }

    @And("each user should have the following required fields:")
    public void eachUserShouldHaveTheFollowingRequiredFields(DataTable dataTable) {
        List<String> requiredFields = dataTable.asList().stream()
                .filter(f -> !f.equals("field"))
                .toList();

        List<User> users = usersData.getFindUsersResponse().getData();

        for (User user : users) {
            for (String field : requiredFields) {
                switch (field) {
                    case "id" -> assertThat(user.getId()).as("User should have id").isNotNull();
                    case "uuid" -> assertThat(user.getUuid()).as("User should have uuid").isNotNull();
                    case "firstname" -> assertThat(user.getFirstname()).as("User should have firstname").isNotNull();
                    case "lastname" -> assertThat(user.getLastname()).as("User should have lastname").isNotNull();
                    case "username" -> assertThat(user.getUsername()).as("User should have username").isNotNull();
                    case "password" -> assertThat(user.getPassword()).as("User should have password").isNotNull();
                    case "email" -> assertThat(user.getEmail()).as("User should have email").isNotNull();
                    case "ip" -> assertThat(user.getIp()).as("User should have ip").isNotNull();
                    case "macAddress" -> assertThat(user.getMacAddress()).as("User should have macAddress").isNotNull();
                    case "website" -> assertThat(user.getWebsite()).as("User should have website").isNotNull();
                }
            }
        }
    }

    @And("all users should have valid UUID format")
    public void allUsersShouldHaveValidUUIDFormat() {
        List<User> users = usersData.getFindUsersResponse().getData();

        for (User user : users) {
            String uuid = user.getUuid();
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
        List<User> users = usersData.getFindUsersResponse().getData();

        for (User user : users) {
            String password = user.getPassword();
            assertThat(password)
                    .as("Password should not be null or empty")
                    .isNotNull()
                    .isNotEmpty();
        }
    }
}
