package com.stockbit.qa.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Model class representing an Address from FakerAPI.
 * Used by Person, Company, and other entities.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
    private int id;
    private String street;
    private String streetName;
    private String buildingNumber;
    private String city;
    private String zipcode;
    private String country;
    @JsonProperty("country_code")
    private String countryCode;
    private double latitude;
    private double longitude;
}

