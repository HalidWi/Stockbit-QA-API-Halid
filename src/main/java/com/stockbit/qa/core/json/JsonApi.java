package com.stockbit.qa.core.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockbit.qa.core.restassured.ResponseApi;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

/**
 * Utility class for JSON operations.
 * Provides methods for converting JSON responses to typed objects.
 */
@Component
public class JsonApi {

    private final ObjectMapper objectMapper;

    public JsonApi() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Converts a REST Assured Response to a typed ResponseApi object.
     *
     * @param response      The REST Assured response
     * @param typeReference Type reference for the expected response body type
     * @param <T>           The type of the response body
     * @return ResponseApi wrapping the response and typed body
     */
    public <T> ResponseApi<T> fromJson(Response response, TypeReference<T> typeReference) {
        try {
            T responseBody = objectMapper.readValue(response.getBody().asString(), typeReference);
            return new ResponseApi<>(response, responseBody);
        } catch (Exception e) {
            // Log the error for debugging
            System.err.println("JSON parsing error: " + e.getMessage());
            e.printStackTrace();
            // Return response with null body if parsing fails
            return new ResponseApi<>(response, null);
        }
    }

    /**
     * Converts a REST Assured Response to a typed ResponseApi object.
     *
     * @param response The REST Assured response
     * @param clazz    Class type for the expected response body
     * @param <T>      The type of the response body
     * @return ResponseApi wrapping the response and typed body
     */
    public <T> ResponseApi<T> fromJson(Response response, Class<T> clazz) {
        try {
            T responseBody = objectMapper.readValue(response.getBody().asString(), clazz);
            return new ResponseApi<>(response, responseBody);
        } catch (Exception e) {
            // Return response with null body if parsing fails
            return new ResponseApi<>(response, null);
        }
    }

    /**
     * Converts an object to JSON string.
     *
     * @param object The object to convert
     * @return JSON string representation
     */
    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            return "{}";
        }
    }

    /**
     * Gets the ObjectMapper instance.
     *
     * @return ObjectMapper instance
     */
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}

