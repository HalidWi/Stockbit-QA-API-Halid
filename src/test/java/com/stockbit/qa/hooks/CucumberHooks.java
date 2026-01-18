package com.stockbit.qa.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cucumber Hooks for test lifecycle management.
 * Handles setup and teardown for each scenario.
 * 
 * Note: Data classes use @ScenarioScope, which automatically creates
 * new instances for each scenario. No manual reset is needed.
 */
public class CucumberHooks {

    private static final Logger logger = LoggerFactory.getLogger(CucumberHooks.class);

    @Before
    public void beforeScenario(Scenario scenario) {
        logger.info("========================================");
        logger.info("Starting Scenario: {}", scenario.getName());
        logger.info("Tags: {}", scenario.getSourceTagNames());
        logger.info("========================================");
    }

    @After
    public void afterScenario(Scenario scenario) {
        logger.info("========================================");
        logger.info("Finished Scenario: {}", scenario.getName());
        logger.info("Status: {}", scenario.getStatus());
        logger.info("========================================");
    }
}
