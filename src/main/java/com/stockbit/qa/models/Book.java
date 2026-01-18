package com.stockbit.qa.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Model class representing a Book from FakerAPI.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    
    private int id;
    private String title;
    private String author;
    private String genre;
    private String description;
    private String isbn;
    private String image;
    private String published;
    private String publisher;
}

