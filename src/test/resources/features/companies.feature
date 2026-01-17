@companies @api
Feature: Companies API
  As a user of FakerAPI
  I want to generate fake company data
  So that I can use it for testing purposes

  Background:
    Given the FakerAPI is available

  @smoke @positive
  Scenario: Successfully retrieve companies with default settings
    When I request companies with quantity 5 and locale "en_US"
    Then the response status code should be 200
    And the response should contain "status" with value "OK"
    And the response should contain exactly 5 companies

  @positive
  Scenario Outline: Retrieve companies with different quantities
    When I request companies with quantity <quantity> and locale "en_US"
    Then the response status code should be 200
    And the response should contain exactly <quantity> companies

    Examples:
      | quantity |
      | 1        |
      | 5        |
      | 10       |

  @positive @validation
  Scenario: Validate company data structure
    When I request companies with quantity 1 and locale "en_US"
    Then the response status code should be 200
    And each company should have the following required fields:
      | field   |
      | id      |
      | name    |
      | email   |
      | phone   |
      | country |
      | website |

  @positive @contact
  Scenario: Verify company contact information exists
    When I request companies with quantity 3 and locale "en_US"
    Then the response status code should be 200
    And each company should have contact information

