package com.stockbit.qa.runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-reports/cucumber.html, json:target/cucumber-reports/cucumber.json, rerun:target/cucumber-reports/rerun.txt")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.stockbit.qa.stepdefinitions, com.stockbit.qa.hooks, com.stockbit.qa.config")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@companies")
@ConfigurationParameter(key = PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = "true")
public class CucumberRunner {
    // This class serves as the Cucumber test runner entry point
    // No code is needed here - the annotations configure Cucumber
}

