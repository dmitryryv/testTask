Feature: Retrieve a book by ID

  Scenario: Get a book with a valid existing ID
    Given I have book details with random valid data
    And I create this book for retrieve
    When I get the created book by ID
    Then the response status code should be 200
    And the retrieve response should contain the book details

  Scenario: Get a book with a non-existent ID
    When I get the created book by fake ID
    Then the response status code should be 404

  Scenario Outline: Get a book with an invalid ID format
    When I get the created book by invalid id format "<idFormat>"
    Then the response status code should be <statusCode>

    Examples:
      | idFormat       | statusCode |
      | abc            | 400        |
      | !@#            | 400        |
      | 1 OR 1=1       | 400        |