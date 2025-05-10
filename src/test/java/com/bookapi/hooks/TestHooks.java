package com.bookapi.hooks;

import com.bookapi.api.BookApiClient;
import com.bookapi.config.ApiConfig;
import io.cucumber.java.*;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.bookapi.specs.CookieSessionManager.getAdminSessionCookie;

public class TestHooks {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestHooks.class);
    private static final List<Integer> CREATED_BOOK_IDS = Collections.synchronizedList(new ArrayList<>());
    private static final BookApiClient bookApiClient = new BookApiClient();
    private static boolean initialized = false;


    @BeforeAll
    public static void beforeScenarios() {
        setAdminCookies();
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        LOGGER.info("Starting scenario: {}", scenario.getName());
        Allure.addAttachment("Scenario", "text/plain", scenario.getName());
    }

/*    @Before
    public void markFlaky(Scenario scenario) {

        if (scenario.getSourceTagNames().contains("@flaky")) {
            Allure.getLifecycle().updateTestCase(testResult -> {
                testResult.getLabels().add(
                        new Label().setName("flaky").setValue("true")
                );
            });
        }
    }*/

    @After
    public void afterScenario(Scenario scenario) {
        LOGGER.info("Finished scenario: {}, Status: {}",
                scenario.getName(), scenario.getStatus());
    }

    @AfterAll
    public static void afterScenarios() {
        cleanUpCreatedData();

    }

    public static void setAdminCookies() {
        if (!initialized) {
            RestAssured.baseURI = ApiConfig.get("base.url");
            RestAssured.requestSpecification = new RequestSpecBuilder()
                    .addCookie("JSESSIONID", getAdminSessionCookie())
                    .setContentType("application/json")
                    .build();
            initialized = true;
        }
    }

    public static void getBookIdForCleanUp(Integer bookId) {
        if (bookId != null) {
            LOGGER.info("Tracking book ID for cleanup: {}", bookId);
            CREATED_BOOK_IDS.add(bookId);
        }
    }

    private static void cleanUpCreatedData() {
        LOGGER.info("Cleaning up {} created books", CREATED_BOOK_IDS.size());

        // Create a copy to avoid concurrent modification
        List<Integer> idsToDelete = new ArrayList<>(CREATED_BOOK_IDS);

        for (Integer id : idsToDelete) {
            try {
                LOGGER.info("Deleting book: {}", id);
                bookApiClient.deleteBook(id);
                CREATED_BOOK_IDS.remove(id);
            } catch (Exception e) {
                LOGGER.warn("Failed to delete book {}: {}", id, e.getMessage());
            }
        }
    }
}