package com.stockbit.qa.api.persons.data;

import com.stockbit.qa.core.models.ApiResponse;
import com.stockbit.qa.models.Person;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Data class for Persons API.
 * Stores request parameters and response data for Persons API calls.
 */
@Data
@Component
public class PersonsData {

    // Request Parameters
    private Integer quantity;
    private String locale;
    private String gender;

    // Response Data
    private ApiResponse<Person> findPersonsResponse;

    /**
     * Resets all data to default values.
     */
    public void reset() {
        this.quantity = null;
        this.locale = null;
        this.gender = null;
        this.findPersonsResponse = null;
    }
}

