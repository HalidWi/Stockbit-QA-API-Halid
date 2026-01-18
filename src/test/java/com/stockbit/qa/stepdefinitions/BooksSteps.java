package com.stockbit.qa.stepdefinitions;

import com.stockbit.qa.api.books.data.BooksData;
import com.stockbit.qa.api.books.services.BooksController;
import com.stockbit.qa.core.data.InterceptorData;
import com.stockbit.qa.models.Book;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Books API feature.
 * Uses Spring DI for controller and data management.
 */
public class BooksSteps {

    @Autowired
    private BooksData booksData;

    @Autowired
    private BooksController booksController;

    @Autowired
    private InterceptorData interceptorData;

    // ================== Set Request Parameters ==================

    @Given("[books] prepare request set quantity to {int}")
    public void prepareRequestSetQuantityTo(int quantity) {
        booksData.setQuantity(quantity);
    }

    @Given("[books] prepare request set locale to {string}")
    public void prepareRequestSetLocaleTo(String locale) {
        booksData.setLocale(locale);
    }

    // ================== Send Requests ==================

    @When("I request books with quantity {int} and locale {string}")
    public void iRequestBooksWithQuantityAndLocale(int quantity, String locale) {
        booksData.setQuantity(quantity);
        booksData.setLocale(locale);
        booksData.setFindBooksResponse(booksController.getBooks().getResponseBody());
    }

    @When("[books] send get books request")
    public void sendGetBooksRequest() {
        booksData.setFindBooksResponse(booksController.getBooks().getResponseBody());
    }

    // ================== Assertions ==================

    @Then("the response should contain exactly {int} books")
    public void theResponseShouldContainExactlyBooks(int expectedCount) {
        assertThat(booksData.getFindBooksResponse().getData())
                .as("Expected exactly %d books", expectedCount)
                .hasSize(expectedCount);
    }

    @Then("[books] response should be success")
    public void responseBooksShouldBeSuccess() {
        assertThat(booksData.getFindBooksResponse().isSuccess())
                .as("Response should be successful")
                .isTrue();
    }

    @Then("[books] response should contain {int} books")
    public void responseShouldContainBooks(int expectedCount) {
        assertThat(booksData.getFindBooksResponse().getData())
                .as("Expected exactly %d books", expectedCount)
                .hasSize(expectedCount);
    }

    @And("each book should have the following required fields:")
    public void eachBookShouldHaveTheFollowingRequiredFields(DataTable dataTable) {
        List<String> requiredFields = dataTable.asList().stream()
                .filter(f -> !f.equals("field"))
                .toList();

        List<Book> books = booksData.getFindBooksResponse().getData();

        for (Book book : books) {
            for (String field : requiredFields) {
                switch (field) {
                    case "id" -> assertThat(book.getId()).as("Book should have id").isNotNull();
                    case "title" -> assertThat(book.getTitle()).as("Book should have title").isNotNull();
                    case "author" -> assertThat(book.getAuthor()).as("Book should have author").isNotNull();
                    case "genre" -> assertThat(book.getGenre()).as("Book should have genre").isNotNull();
                    case "description" -> assertThat(book.getDescription()).as("Book should have description").isNotNull();
                    case "isbn" -> assertThat(book.getIsbn()).as("Book should have isbn").isNotNull();
                    case "image" -> assertThat(book.getImage()).as("Book should have image").isNotNull();
                    case "published" -> assertThat(book.getPublished()).as("Book should have published").isNotNull();
                    case "publisher" -> assertThat(book.getPublisher()).as("Book should have publisher").isNotNull();
                }
            }
        }
    }

    @And("all books should have valid ISBN format")
    public void allBooksShouldHaveValidISBNFormat() {
        List<Book> books = booksData.getFindBooksResponse().getData();

        for (Book book : books) {
            String isbn = book.getIsbn();
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
