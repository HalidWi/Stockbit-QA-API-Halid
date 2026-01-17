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
    private List<Contact> contact;
    private List<String> addresses;
    
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
}

