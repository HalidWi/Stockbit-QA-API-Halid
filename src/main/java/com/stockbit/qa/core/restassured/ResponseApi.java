package com.stockbit.qa.core.restassured;

import io.restassured.response.Response;
import lombok.Data;

/**
 * Wrapper class for REST Assured Response with typed body.
 *
 * @param <T> The type of the response body
 */
@Data
public class ResponseApi<T> {

    private Response response;
    private T responseBody;

    public ResponseApi(Response response, T responseBody) {
        this.response = response;
        this.responseBody = responseBody;
    }

    /**
     * Get the HTTP status code.
     *
     * @return HTTP status code
     */
    public int getStatusCode() {
        return response.getStatusCode();
    }

    /**
     * Check if the request was successful (2xx status code).
     *
     * @return true if status code is 2xx
     */
    public boolean isSuccess() {
        int statusCode = response.getStatusCode();
        return statusCode >= 200 && statusCode < 300;
    }

    /**
     * Get the raw response body as string.
     *
     * @return Response body as string
     */
    public String getBodyAsString() {
        return response.getBody().asString();
    }

    /**
     * Pretty print the response body.
     */
    public void prettyPrint() {
        response.getBody().prettyPrint();
    }
}

