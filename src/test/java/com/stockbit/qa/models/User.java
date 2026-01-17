package com.stockbit.qa.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Model class representing a User from FakerAPI.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    
    private int id;
    private String uuid;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String ip;
    private String macAddress;
    private String website;
    private String image;
}

