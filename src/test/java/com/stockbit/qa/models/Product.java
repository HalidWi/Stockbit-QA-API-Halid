package com.stockbit.qa.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Model class representing a Product from FakerAPI.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    
    private int id;
    private String name;
    private String description;
    private String ean;
    private String upc;
    private String image;
    private List<String> images;
    private double price;
    private List<String> categories;
    private double net_price;
    private List<Tax> taxes;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Tax {
        private String name;
        private double value;
    }
}

