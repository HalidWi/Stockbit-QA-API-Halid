@products @api
Feature: Products API
  As a user of FakerAPI
  I want to generate fake product data
  So that I can use it for testing purposes

  Background:
    Given the FakerAPI is available

  @smoke @positive
  Scenario: Successfully retrieve products with default settings
    When I request products with quantity 5 and locale "en_US"
    Then the response status code should be 200
    And the response should contain "status" with value "OK"
    And the response should contain exactly 5 products

  @positive
  Scenario Outline: Retrieve products with different quantities
    When I request products with quantity <quantity> and locale "en_US"
    Then the response status code should be 200
    And the response should contain exactly <quantity> products

    Examples:
      | quantity |
      | 1        |
      | 5        |
      | 10       |

  @positive @validation
  Scenario: Validate product data structure
    When I request products with quantity 1 and locale "en_US"
    Then the response status code should be 200
    And each product should have the following required fields:
      | field       |
      | id          |
      | name        |
      | description |
      | price       |

  @positive @price
  Scenario: Verify products have positive prices
    When I request products with quantity 10 and locale "en_US"
    Then the response status code should be 200
    And all products should have positive prices

  @positive @taxes
  Scenario: Retrieve products with price and taxes
    When I request products with quantity 5 and locale "en_US"
    Then the response status code should be 200
    And the product prices should be consistent with taxes

