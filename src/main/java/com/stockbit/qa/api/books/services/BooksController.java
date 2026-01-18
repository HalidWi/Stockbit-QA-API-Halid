package com.stockbit.qa.api.books.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.stockbit.qa.api.books.data.BooksData;
import com.stockbit.qa.core.json.JsonApi;
import com.stockbit.qa.core.models.ApiResponse;
import com.stockbit.qa.core.properties.EndpointProperties;
import com.stockbit.qa.core.restassured.ResponseApi;
import com.stockbit.qa.core.restassured.ServiceApi;
import com.stockbit.qa.models.Book;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller for Books API.
 * Handles all REST API calls related to books.
 */
@Component
public class BooksController extends ServiceApi {

    @Autowired
    private JsonApi jsonApi;

    @Autowired
    private BooksData booksData;

    @Autowired
    private EndpointProperties endpointProperties;

    /**
     * Get books with configured parameters.
     *
     * @return ResponseApi with ApiResponse containing list of Book
     */
    public ResponseApi<ApiResponse<Book>> getBooks() {
        RequestSpecification requestSpec = service("fakerapi");

        // Override quantity if set in booksData
        if (booksData.getQuantity() != null) {
            requestSpec.queryParam("_quantity", booksData.getQuantity());
        }

        // Override locale if set in booksData
        if (booksData.getLocale() != null && !booksData.getLocale().isEmpty()) {
            requestSpec.queryParam("_locale", booksData.getLocale());
        }

        Response response = requestSpec.get(endpointProperties.getBooks());
        response.getBody().prettyPrint();

        return jsonApi.fromJson(response, new TypeReference<ApiResponse<Book>>() {});
    }
}

