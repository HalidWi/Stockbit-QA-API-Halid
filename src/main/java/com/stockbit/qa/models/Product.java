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
    private List<ProductImage> images;
    private double price;
    private List<Integer> categories;
    private double net_price;
    private int taxes;  // API returns taxes as an integer percentage
    private List<String> tags;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductImage {
        private String title;
        private String description;
        private String url;
    }
}

