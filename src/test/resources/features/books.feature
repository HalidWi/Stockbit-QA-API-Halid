@books @api
Feature: Books API
  As a user of FakerAPI
  I want to generate fake book data
  So that I can use it for testing purposes

  Background:
    Given the FakerAPI is available

  @smoke @positive
  Scenario: Successfully retrieve books with default settings
    When I request books with quantity 5 and locale "en_US"
    Then the response status code should be 200
    And the response should contain "status" with value "OK"
    And the response should contain exactly 5 books

  @positive
  Scenario Outline: Retrieve books with different quantities
    When I request books with quantity <quantity> and locale "en_US"
    Then the response status code should be 200
    And the response should contain exactly <quantity> books

    Examples:
      | quantity |
      | 1        |
      | 5        |
      | 15       |

  @positive @validation
  Scenario: Validate book data structure
    When I request books with quantity 1 and locale "en_US"
    Then the response status code should be 200
    And each book should have the following required fields:
      | field       |
      | id          |
      | title       |
      | author      |
      | genre       |
      | description |
      | isbn        |
      | published   |
      | publisher   |

  @positive @isbn
  Scenario: Verify ISBN format in book data
    When I request books with quantity 5 and locale "en_US"
    Then the response status code should be 200
    And all books should have valid ISBN format

