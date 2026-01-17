@users @api
Feature: Users API
  As a user of FakerAPI
  I want to generate fake user data
  So that I can use it for testing purposes

  Background:
    Given the FakerAPI is available

  @smoke @positive
  Scenario: Successfully retrieve users with default settings
    When I request users with quantity 5 and locale "en_US"
    Then the response status code should be 200
    And the response should contain "status" with value "OK"
    And the response should contain exactly 5 users

  @positive
  Scenario Outline: Retrieve users with different quantities
    When I request users with quantity <quantity> and locale "en_US"
    Then the response status code should be 200
    And the response should contain exactly <quantity> users

    Examples:
      | quantity |
      | 1        |
      | 5        |
      | 10       |

  @positive @validation
  Scenario: Validate user data structure
    When I request users with quantity 1 and locale "en_US"
    Then the response status code should be 200
    And each user should have the following required fields:
      | field     |
      | id        |
      | uuid      |
      | firstname |
      | lastname  |
      | username  |
      | password  |
      | email     |

  @positive @uuid
  Scenario: Verify UUID format in user data
    When I request users with quantity 5 and locale "en_US"
    Then the response status code should be 200
    And all users should have valid UUID format

  @positive @password
  Scenario: Verify users have non-empty passwords
    When I request users with quantity 5 and locale "en_US"
    Then the response status code should be 200
    And all users should have non-empty passwords

