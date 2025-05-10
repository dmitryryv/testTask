Feature: Retrieve all books

  Scenario: Get a list of books when the database contains records
    Given I have book details with required fields only and random valid data
    When I create a new book
    When I request all books
    Then the response status code should be 200
    And the response should contain a list of books
    And every book should contain all required fields

  Scenario: Get books with invalid query parameter
    When I request all books with incorrect query parameter "?author=Martin Fowler'); DROP TABLE books; --"
    Then the response status code should be 400

# I could clean the 'book' table as a pre-condition and add several books as post-condition,
# but I'm just not sure that this is a good idea

#  Scenario: Get a list of books when the database is empty
#    Given the database is empty
#    When I request all books
#    Then the response status code should be 200
#    And the response body should be an empty array
