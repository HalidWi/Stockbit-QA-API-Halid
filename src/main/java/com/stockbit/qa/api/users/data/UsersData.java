package com.stockbit.qa.api.users.data;

import com.stockbit.qa.core.models.ApiResponse;
import com.stockbit.qa.models.User;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Data class for Users API.
 * Stores request parameters and response data for Users API calls.
 */
@Data
@Component
public class UsersData {

    // Request Parameters
    private Integer quantity;
    private String locale;
    private String gender;

    // Response Data
    private ApiResponse<User> findUsersResponse;

    /**
     * Resets all data to default values.
     */
    public void reset() {
        this.quantity = null;
        this.locale = null;
        this.gender = null;
        this.findUsersResponse = null;
    }
}

