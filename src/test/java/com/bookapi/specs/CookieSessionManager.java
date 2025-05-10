package com.bookapi.specs;

import com.bookapi.config.ApiConfig;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class CookieSessionManager {
    private static String adminSessionCookie;

    public static String getAdminSessionCookie() {
        if (adminSessionCookie == null) {
            String username = ApiConfig.get("admin.username");
            String password = ApiConfig.get("admin.password");

            adminSessionCookie = given()
                    .auth().preemptive().basic(username, password)
                    .contentType(ContentType.JSON)
                    .get()
                    .getCookie("JSESSIONID");
        }
        return adminSessionCookie;
    }

}