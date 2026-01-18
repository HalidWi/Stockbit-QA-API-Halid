package com.stockbit.qa.api.books.data;

import com.stockbit.qa.core.models.ApiResponse;
import com.stockbit.qa.models.Book;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Data class for Books API.
 * Stores request parameters and response data for Books API calls.
 */
@Data
@Component
public class BooksData {

    // Request Parameters
    private Integer quantity;
    private String locale;

    // Response Data
    private ApiResponse<Book> findBooksResponse;

    /**
     * Resets all data to default values.
     */
    public void reset() {
        this.quantity = null;
        this.locale = null;
        this.findBooksResponse = null;
    }
}

