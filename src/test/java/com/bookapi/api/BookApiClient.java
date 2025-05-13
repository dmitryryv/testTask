package com.bookapi.api;

import com.bookapi.models.BookRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static com.bookapi.constants.ApiEndpoints.BOOKS;
import static com.bookapi.constants.ApiEndpoints.BOOK_BY_ID;
import static io.restassured.RestAssured.given;

public class BookApiClient {
    @Step("Get all books")
    public Response getAllBooks() {
        return given()
                .get(BOOKS.getPath());
    }

    @Step("Get book by ID: {id}")
    public Response getBookById(int id) {
        return given()
                .pathParam("id", id)
                .get(BOOK_BY_ID.getPath());
    }

    @Step("Create new book")
    public Response createBook(BookRequest book) {
        return given()
                .body(book)
                .post(BOOKS.getPath());
    }

    @Step("Update book with ID: {id}")
    public Response updateBook(int id, BookRequest book) {
        return given()
                .pathParam("id", id)
                .body(book)
                .put(BOOK_BY_ID.getPath());
    }

    @Step("Delete book with ID: {id}")
    public Response deleteBook(int id) {
        return given()
                .pathParam("id", id)
                .delete(BOOK_BY_ID.getPath());
    }
}
