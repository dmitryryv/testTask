package com.bookapi.helpers;

import com.bookapi.models.BookRequest;
import com.bookapi.models.BookResponse;
import org.testng.Assert;

public class BookValidator {

    public static void validateBookFields(BookResponse actual, BookRequest expected, String context) {
        Assert.assertNotNull(actual.getId(),
                String.format("%s book should have an ID but got null", context));

        Assert.assertEquals(actual.getName(), expected.getName(),
                String.format("%s book name mismatch. Expected: '%s', Actual: '%s'",
                        context, expected.getName(), actual.getName()));

        Assert.assertEquals(actual.getAuthor(), expected.getAuthor(),
                String.format("%s book author mismatch. Expected: '%s', Actual: '%s'",
                        context, expected.getAuthor(), actual.getAuthor()));

        Assert.assertEquals(actual.getPublication(), expected.getPublication(),
                String.format("%s book publication mismatch. Expected: '%s', Actual: '%s'",
                        context, expected.getPublication(), actual.getPublication()));

        Assert.assertEquals(actual.getCategory(), expected.getCategory(),
                String.format("%s book category mismatch. Expected: '%s', Actual: '%s'",
                        context, expected.getCategory(), actual.getCategory()));

        Assert.assertEquals(actual.getPages(), expected.getPages(),
                String.format("%s book pages count mismatch. Expected: %d, Actual: %d",
                        context, expected.getPages(), actual.getPages()));

        Assert.assertEquals(actual.getPrice(), expected.getPrice(),
                String.format("%s book price mismatch. Expected: %.2f, Actual: %.2f",
                        context, expected.getPrice(), actual.getPrice()));
    }

    public static void validateBookResponseFields(BookResponse actual, BookResponse expected, String context) {
        Assert.assertNotNull(actual.getId(),
                String.format("%s book should have an ID but got null", context));

        Assert.assertEquals(actual.getName(), expected.getName(),
                String.format("%s book name mismatch. Expected: '%s', Actual: '%s'",
                        context, expected.getName(), actual.getName()));

        Assert.assertEquals(actual.getAuthor(), expected.getAuthor(),
                String.format("%s book author mismatch. Expected: '%s', Actual: '%s'",
                        context, expected.getAuthor(), actual.getAuthor()));

        Assert.assertEquals(actual.getPublication(), expected.getPublication(),
                String.format("%s book publication mismatch. Expected: '%s', Actual: '%s'",
                        context, expected.getPublication(), actual.getPublication()));

        Assert.assertEquals(actual.getCategory(), expected.getCategory(),
                String.format("%s book category mismatch. Expected: '%s', Actual: '%s'",
                        context, expected.getCategory(), actual.getCategory()));

        Assert.assertEquals(actual.getPages(), expected.getPages(),
                String.format("%s book pages count mismatch. Expected: %d, Actual: %d",
                        context, expected.getPages(), actual.getPages()));

        Assert.assertEquals(actual.getPrice(), expected.getPrice(),
                String.format("%s book price mismatch. Expected: %.2f, Actual: %.2f",
                        context, expected.getPrice(), actual.getPrice()));
    }

    public static void validateShortBookFields(BookResponse actual, BookRequest expected) {
        Assert.assertNotNull(actual.getId(),
                "Created book should have an ID but got null");

        Assert.assertEquals(actual.getName(), expected.getName(),
                String.format("Book name mismatch. Expected: '%s', Actual: '%s'",
                        expected.getName(), actual.getName()));

        Assert.assertEquals(actual.getAuthor(), expected.getAuthor(),
                String.format("Book author mismatch. Expected: '%s', Actual: '%s'",
                        expected.getAuthor(), actual.getAuthor()));

        Assert.assertEquals(actual.getPublication(), expected.getPublication(),
                String.format("Book publication mismatch. Expected: '%s', Actual: '%s'",
                        expected.getPublication(), actual.getPublication()));

        Assert.assertEquals(actual.getCategory(), expected.getCategory(),
                String.format("Book category mismatch. Expected: '%s', Actual: '%s'",
                        expected.getCategory(), actual.getCategory()));

        Assert.assertEquals(actual.getPages(), 0,
                String.format("Book pages count should be 0 for short book, but got: %d",
                        actual.getPages()));

        Assert.assertEquals(actual.getPrice(), 0.0,
                String.format("Book price should be 0.0 for short book, but got: %.2f",
                        actual.getPrice()));
    }

    public static void validateResponseStatusCode(int actualStatusCode, int expectedStatusCode, String operation, String responseBody) {
        Assert.assertEquals(actualStatusCode, expectedStatusCode,
                String.format("%s failed. Expected status code %d but got %d. Response body: %s",
                        operation, expectedStatusCode, actualStatusCode, responseBody));
    }
} 