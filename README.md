# Book API Automated Test Suite

## 1. Application Under Test

This project contains automated tests for a **Book API** application.  
The Book API provides endpoints to manage a collection of books, supporting operations such as:

- Creating a new book (with required and optional fields)
- Retrieving a book by ID (including edge cases: non-existent, invalid, or special-character IDs)
- Updating book details (with validation for required fields and data integrity)
- Deleting books (including handling of invalid or non-existent IDs)
- Querying books with filters and query parameters

The API is expected to validate input data, handle error scenarios gracefully, and return appropriate HTTP status codes.

---

## 2. Automated Test Cases

The test suite is implemented in **Java** using **Cucumber** for BDD-style scenarios and **TestNG** as the test runner.  
Key features of the test suite:

- **Positive and negative scenarios** for all main API operations
- Validation of required fields, data types, and business rules
- Edge case coverage (e.g., SQL injection, invalid formats, missing/empty bodies)
- Automated defect detection with clear actual vs. expected results
- Allure reporting for rich, interactive test results

**Test Structure:**
- Feature files are located in `src/test/resources/features/`
- Step definitions and hooks are in `src/test/java/`
- Test data is provided via data tables and attachments

---

## 3. How to Run Locally and Generate Allure Report

### **Prerequisites**

- Java 17 or higher
- Maven 3.8+
- Allure 2.27+
- (Optional) [Allure Commandline](https://docs.qameta.io/allure/#_installing_a_commandline)

### **Steps**

1. **Clone the repository:**
    ```sh
    git clone https://github.com/dmitryryv/testTask.git
    cd testTask
    ```

2. **Run the tests with Maven:**
    ```sh
    mvn clean test
    ```

   This will execute all automated tests and generate Allure result files in the `target/allure-results` directory.


3. **Generate and view the Allure report:**

    - If you have Allure installed:
        ```sh
        allure serve target/allure-results
        ```
      This will generate the report and open it in your browser.

    - Or, to generate static HTML:
        ```sh
        allure generate target/allure-results --clean -o target/allure-report
        ```

      Then open `target/allure-report/index.html` in your browser.

---

## 4. Additional Notes

- Test results and defect details are available in the Allure report.
- For custom test data or environment configuration, see the `src/test/java` directory.
- For troubleshooting, check the console logs and the Allure report for failed scenarios and stack traces.

---

**Happy Testing!**