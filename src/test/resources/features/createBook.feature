Feature: Create a new book

  @flaky
  Scenario: Create a book with all fields
    Given I have book details:
      | name                                               | author        | publication                 | category    | pages | price |
      | Refactoring: Improving the Design of Existing Code | Martin Fowler | Addison-Wesley Professional | Programming | 448   | 35.50 |
    When I create a new book
    Then the response status code should be 200
    And the response should contain the book details
    And the book should be available when requested by ID

  @flaky
  Scenario: Create a book with required fields
    Given I have book details with required fields only and random valid data
    When I create a new book
    Then the response status code should be 200
    And the response should contain the created short book details
    And the book should be available when requested by ID

  Scenario: Create a book with missing required fields
    Given I have book details with missing required fields:
      | publication                 | category    |
      | Addison-Wesley Professional | Programming |
    When I create a new book which should not be created
    Then the response status code should be 400

  Scenario: Create a book with several null required fields
    Given I have book details with several null required fields
    When I create a new book which should not be created
    Then the response status code should be 400

  Scenario: Create a book with null name
    Given I have book details with null name
    When I create a new book which should not be created
    Then the response status code should be 400

  Scenario: Create a book with null author
    Given I have book details with null author
    When I create a new book which should not be created
    Then the response status code should be 400

  Scenario: Create a book with null publication
    Given I have book details with null publication
    When I create a new book which should not be created
    Then the response status code should be 400

  Scenario: Create a book with null category
    Given I have book details with null category
    When I create a new book which should not be created
    Then the response status code should be 400

  Scenario: Create a book with empty body
    Given I have book details without body
    When I create a new book which should not be created
    Then the response status code should be 400

  Scenario: Create a book with negative price
    Given I have book details with negative price
    When I create a new book which should not be created
    Then the response status code should be 400

  Scenario: Create a book with negative pages count
    Given I have book details with negative price
    When I create a new book which should not be created
    Then the response status code should be 400


