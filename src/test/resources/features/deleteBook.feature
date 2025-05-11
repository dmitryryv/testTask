Feature: Delete a book by ID

  @flaky
  Scenario: Successfully delete an existing book
    Given I have book details with random valid data
    And I create this book
    When I delete the created book
    Then the response status code should be 200
    And the response should contain the book details
    And the deleted book should no longer be available

  Scenario: Delete a book with a non-existent ID
    When I delete book with non-existent id
    Then the response status code should be 404

  Scenario: Delete the same book twice
    Given I have book details with random valid data
    And I create this book
    When I delete the created book
    When I delete the created book
    Then the response status code should be 404
    And the deleted book should no longer be available

  Scenario Outline: Get a book with an invalid ID format
    When I get the created book by invalid id format "<idFormat>"
    Then the response status code should be <statusCode>

    Examples:
      | idFormat       | statusCode |
      | word           | 400        |
      | !@#            | 400        |