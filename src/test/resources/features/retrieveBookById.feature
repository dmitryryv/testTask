Feature: Retrieve a book by ID
  @flaky
  Scenario: Get a book with a valid existing ID
    Given I have book details with random valid data
    And I create this book
    When I get the created book by ID
    Then the response status code should be 200
    And the response should contain the book details

  Scenario: Get a book with a non-existent ID
    When I get the created book by fake ID
   Then the response status code should be 404

  Scenario: Get a book with a non-numeric ID
    When I get the created book by invalid id format "abc"
    Then the response status code should be 400

  Scenario: Get a book with a special character ID
    When I get the created book by invalid id format "!@#"
    Then the response status code should be 400

  Scenario: Get a book using SQL injection
    When I get the created book by invalid id format "1 OR 1=1"
    Then the response status code should be 400