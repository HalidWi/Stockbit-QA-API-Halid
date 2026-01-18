package com.stockbit.qa.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Model class representing a Company from FakerAPI.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {
    
    private int id;
    private String name;
    private String email;
    private String vat;
    private String phone;
    private String country;
    private String website;
    private String image;
    private Contact contact;  // Single contact object, not a list
    private List<Address> addresses;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Contact {
        private int id;
        private String firstname;
        private String lastname;
        private String email;
        private String phone;
        private String birthday;
        private String gender;
    }
    
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
        private String country_code;
        private double latitude;
        private double longitude;
    }
}

