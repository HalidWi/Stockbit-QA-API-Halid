@persons @api
Feature: Persons API
  As a user of FakerAPI
  I want to generate fake person data
  So that I can use it for testing purposes

  Background:
    Given the FakerAPI is available

  @smoke @positive
  Scenario: Successfully retrieve persons with default settings
    When I request persons with quantity 5 and locale "en_US"
    Then the response status code should be 200
    And the response should contain "status" with value "OK"
    And the response should contain exactly 5 persons

  @positive
  Scenario Outline: Retrieve persons with different quantities
    When I request persons with quantity <quantity> and locale "en_US"
    Then the response status code should be 200
    And the response should contain "status" with value "OK"
    And the response should contain exactly <quantity> persons

    Examples:
      | quantity |
      | 1        |
      | 3        |
      | 10       |

  @positive @validation
  Scenario: Validate person data structure
    When I request persons with quantity 1 and locale "en_US"
    Then the response status code should be 200
    And each person should have the following required fields:
      | field     |
      | id        |
      | firstname |
      | lastname  |
      | email     |
      | phone     |
      | birthday  |
      | gender    |

  @positive @email
  Scenario: Verify email format in person data
    When I request persons with quantity 5 and locale "en_US"
    Then the response status code should be 200
    And all persons should have valid email format

  @positive @gender
  Scenario: Verify gender values in person data
    When I request persons with quantity 10 and locale "en_US"
    Then the response status code should be 200
    And all persons should have gender either "male" or "female"

  @negative @boundary
  Scenario: Request persons with zero quantity
    When I request persons with quantity 0 and locale "en_US"
    Then the response status code should be 200
    And the response should contain 0 or more persons

