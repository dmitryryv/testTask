package com.bookapi.helpers;

import com.github.javafaker.Faker;

import java.util.Random;

public class BookDataFaker {
    private final Faker faker;
    private final Random random;

    public BookDataFaker() {
        this.faker = new Faker();
        this.random = new Random();
    }

    public String getBookName() {
        return faker.book().title();
    }

    public String getBookAuthor() {
        return faker.book().author();
    }

    public String getBookPublication() {
        return faker.book().publisher();
    }

    public String getBookCategory() {
        return faker.book().genre();
    }

    public int getPagesNumber() {
        return random.nextInt(900) + 100;
    }

    public double getPriceNumber() {
        return Math.round((random.nextDouble() * 90 + 10) * 100.0) / 100.0;
    }


    public double getNegativeDoubleNumber() {
        return -19.99;
    }

    public int getNegativeIntNumber() {
        return -100500;
    }

    public int getBigIntNumber() {
        return Math.round((random.nextInt() * 90 + 10) * 1000000);
    }
}

