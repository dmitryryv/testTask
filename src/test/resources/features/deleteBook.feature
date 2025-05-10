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

  Scenario: Delete a book with invalid ID format
    When I delete book with invalid id format: "word"
    Then the response status code should be 400

  Scenario: Delete a book with invalid ID format
    When I delete book with invalid id format: "!@#"
    Then the response status code should be 400


  Scenario: Delete the same book twice
    Given I have book details with random valid data
    And I create this book
    When I delete the created book
    When I delete the created book
    Then the response status code should be 404
    And the deleted book should no longer be available
