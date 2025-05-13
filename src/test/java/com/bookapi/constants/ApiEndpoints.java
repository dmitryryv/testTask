package com.bookapi.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum ApiEndpoints {
    BOOKS("api/v1/books"),
    BOOK_BY_ID("api/v1/books/{id}");

    private final String path;
} 