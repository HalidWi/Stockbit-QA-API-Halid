package com.stockbit.qa.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Model class representing a Person from FakerAPI.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
    
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String birthday;
    private String gender;
    private Address address;
    private String website;
    private String image;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {
        private int id;
        private String street;
        private String streetName;
        private String buildingNumber;
        private String city;
        private String zipcode;
        private String country;
        @JsonProperty("county_code")
        private String countyCode;
        private double latitude;
        private double longitude;
    }
}

