package com.bookapi.api;

import com.bookapi.models.BookRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BookApiClient {
    public static final String BOOKS_ENDPOINT = "api/v1/books";
    public static final String BOOK_BY_ID_ENDPOINT = "api/v1/books/{id}";

    @Step("Get all books")
    public Response getAllBooks() {
        return given()
                .get(BOOKS_ENDPOINT);
    }

    @Step("Get book by ID: {id}")
    public Response getBookById(int id) {
        return given()
                .pathParam("id", id)
                .get(BOOK_BY_ID_ENDPOINT);
    }

    @Step("Create new book")
    public Response createBook(BookRequest book) {
        return given()
                .body(book)
                .post(BOOKS_ENDPOINT);
    }

    @Step("Update book with ID: {id}")
    public Response updateBook(int id, BookRequest book) {
        return given()
                .pathParam("id", id)
                .body(book)
                .put(BOOK_BY_ID_ENDPOINT);
    }

    @Step("Delete book with ID: {id}")
    public Response deleteBook(int id) {
        return given()
                .pathParam("id", id)
                .delete(BOOK_BY_ID_ENDPOINT);
    }

    @Step("Delete book with ID: {id}")
    public Response deleteBookWithInco(int id) {
        return given()
                .pathParam("id", id)
                .delete(BOOK_BY_ID_ENDPOINT);
    }
}
