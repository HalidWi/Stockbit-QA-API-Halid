package com.stockbit.qa.core.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Generic API Response wrapper for FakerAPI responses.
 *
 * @param <T> The type of data in the response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse<T> {

    private String status;
    private int code;
    private String locale;
    private int total;
    private List<T> data;

    /**
     * Check if the response was successful.
     *
     * @return true if status is "OK"
     */
    public boolean isSuccess() {
        return "OK".equalsIgnoreCase(status);
    }
}

