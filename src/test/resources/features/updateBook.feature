Feature: Update an existing book

  Scenario: Update a book with valid data
    Given I have book details:
      | name               | author        | publication      | category | pages | price |
      | Book Before Update | Update Author | Update Publisher | Test     | 200   | 20.50 |
    And I create this book
    When I update the created book with the following details:
      | name              | author         | publication       | category | pages | price |
      | Book After Update | Updated Author | Updated Publisher | Updated  | 250   | 25.99 |
    Then the response status code should be 200
    And the response should contain the book details
    And the book should be permanently updated with new values

  Scenario: Update a non-existent book
    When I update a book with fake id
    Then the response status code should be 404

  Scenario: Update a book with invalid ID format
    When I update a book with invalid id format: "abc"
    Then the response status code should be 400

  Scenario: Update a book with missing required fields
    Given I have book details with random valid data
    And I create this book
    When I update the created book with missing required fields
    Then the response status code should be 400

  Scenario: Update a book with empty body
    Given I have book details with random valid data
    And I create this book
    When I update the created book with empty body
    Then the response status code should be 400