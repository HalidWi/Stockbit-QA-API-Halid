package com.stockbit.qa.context;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Test Context class for sharing state between step definitions.
 * Implements Singleton pattern for state management across scenarios.
 */
public class TestContext {
    
    private Response response;
    private final Map<String, Object> scenarioContext;
    
    public TestContext() {
        this.scenarioContext = new HashMap<>();
    }
    
    public Response getResponse() {
        return response;
    }
    
    public void setResponse(Response response) {
        this.response = response;
    }
    
    public void setContext(String key, Object value) {
        scenarioContext.put(key, value);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getContext(String key) {
        return (T) scenarioContext.get(key);
    }
    
    public boolean containsKey(String key) {
        return scenarioContext.containsKey(key);
    }
    
    public void clearContext() {
        scenarioContext.clear();
        response = null;
    }
}

