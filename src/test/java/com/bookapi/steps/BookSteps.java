package com.bookapi.steps;

import com.bookapi.api.BookApiClient;
import com.bookapi.helpers.BookDataFaker;
import com.bookapi.hooks.TestHooks;
import com.bookapi.models.BookRequest;
import com.bookapi.models.BookResponse;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

import static com.bookapi.api.BookApiClient.BOOKS_ENDPOINT;
import static com.bookapi.api.BookApiClient.BOOK_BY_ID_ENDPOINT;
import static io.restassured.RestAssured.given;


public class BookSteps {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookSteps.class);
    private final BookApiClient bookApiClient = new BookApiClient();
    BookDataFaker faker = new BookDataFaker();

    private final ThreadLocal<Response> response = new ThreadLocal<>();
    private final ThreadLocal<BookRequest> bookRequest = new ThreadLocal<>();
    private final ThreadLocal<BookRequest> updateRequest = new ThreadLocal<>();
    private final ThreadLocal<List<Map<String, Object>>> allBooks = new ThreadLocal<>();
    private final ThreadLocal<BookResponse> bookResponse = new ThreadLocal<>();
    private final ThreadLocal<Integer> bookIdForTest = new ThreadLocal<>();

    @Given("I have book details:")
    public void iHaveBookDetails(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> data = rows.get(0);

        bookRequest.set(BookRequest.builder()
                .name(data.get("name"))
                .author(data.get("author"))
                .publication(data.get("publication"))
                .category(data.get("category"))
                .pages(Integer.parseInt(data.get("pages")))
                .price(Double.parseDouble(data.get("price")))
                .build());
    }

    @Given("I have book details with random valid data")
    public void iHaveBookDetailsWithRandomValidData() {

        bookRequest.set(BookRequest.builder()
                .name(faker.getBookName())
                .author(faker.getBookAuthor())
                .publication(faker.getBookPublication())
                .category(faker.getBookCategory())
                .pages(faker.getPagesNumber())
                .price(faker.getPriceNumber())
                .build());
    }

    @Given("I have book details with negative price")
    public void iHaveBookDetailsWithNegativePrice() {

        bookRequest.set(BookRequest.builder()
                .name(faker.getBookName())
                .author(faker.getBookAuthor())
                .publication(faker.getBookPublication())
                .category(faker.getBookCategory())
                .pages(faker.getNegativeIntNumber())
                .price(faker.getPriceNumber())
                .build());
    }

    @Given("I have book details with negative pages count")
    public void iHaveBookDetailsWithNegativePagesCount() {

        bookRequest.set(BookRequest.builder()
                .name(faker.getBookName())
                .author(faker.getBookAuthor())
                .publication(faker.getBookPublication())
                .category(faker.getBookCategory())
                .pages(faker.getPagesNumber())
                .price(faker.getNegativeDoubleNumber())
                .build());
    }

    @Given("I have book details with required fields only and random valid data")
    public void iHaveBookDetailsWithRequiredFieldsOnlyAndRandomValidData() {

        bookRequest.set(BookRequest.builder()
                .name(faker.getBookName())
                .author(faker.getBookAuthor())
                .publication(faker.getBookPublication())
                .category(faker.getBookCategory())
                .build());
    }

    @Given("I have book details with missing required fields:")
    public void iHaveBookDetailsWithMissingRequiredFields(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> data = rows.get(0);

        bookRequest.set(BookRequest.builder()
                .publication(data.get("publication"))
                .category(data.get("category"))
                .build());
    }

    @Given("I have book details with several null required fields")
    public void iHaveBookDetailsWithNullRequiredFields() {

        bookRequest.set(BookRequest.builder()
                .name(null)
                .author(null)
                .publication(null)
                .category(faker.getBookCategory())
                .pages(faker.getPagesNumber())
                .price(faker.getNegativeDoubleNumber())
                .build());
    }


    @Given("I have book details with several null name")
    public void iHaveBookDetailsWithNullName() {

        bookRequest.set(BookRequest.builder()
                .name(null)
                .author(faker.getBookAuthor())
                .publication(faker.getBookPublication())
                .category(faker.getBookCategory())
                .pages(faker.getPagesNumber())
                .price(faker.getNegativeDoubleNumber())
                .build());
    }


    @Given("I have book details with several null author")
    public void iHaveBookDetailsWithNullAuthor() {

        bookRequest.set(BookRequest.builder()
                .name(faker.getBookName())
                .author(null)
                .publication(faker.getBookPublication())
                .category(faker.getBookCategory())
                .pages(faker.getPagesNumber())
                .price(faker.getNegativeDoubleNumber())
                .build());
    }


    @Given("I have book details with several null publication")
    public void iHaveBookDetailsWithNullPublication() {

        bookRequest.set(BookRequest.builder()
                .name(faker.getBookName())
                .author(faker.getBookAuthor())
                .publication(null)
                .category(faker.getBookCategory())
                .pages(faker.getPagesNumber())
                .price(faker.getNegativeDoubleNumber())
                .build());
    }


    @Given("I have book details with several null category")
    public void iHaveBookDetailsWithNullCategory() {

        bookRequest.set(BookRequest.builder()
                .name(faker.getBookName())
                .author(faker.getBookAuthor())
                .publication(faker.getBookPublication())
                .category(null)
                .pages(faker.getPagesNumber())
                .price(faker.getNegativeDoubleNumber())
                .build());
    }

    @Given("I have book details without body")
    public void iHaveBookDetailsWithoutBody() {

        bookRequest.set(BookRequest.builder().build());
    }

    @Given("I create this book")
    public void iCreateThisBook() {
        response.set(bookApiClient.createBook(bookRequest.get()));
        Assert.assertEquals(response.get().getStatusCode(), 200, "Failed to create book");

        bookResponse.set(response.get().as(BookResponse.class));
        bookIdForTest.set(bookResponse.get().getId());
        Assert.assertNotNull(bookIdForTest.get(), "Failed to get ID for the created book");

        TestHooks.getBookIdForCleanUp(bookIdForTest.get());
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        Assert.assertEquals(response.get().getStatusCode(), expectedStatusCode);
    }

    @When("I request all books")
    public void iRequestAllBooks() {
        response.set(bookApiClient.getAllBooks());
    }

    @When("I request all books with incorrect query parameter {string}")
    public void iRequestAllBooksWithIncorrectQueryParameter(String query) {
        Response response1 = given().get(BOOKS_ENDPOINT + query);

        response.set(response1);
    }

    @Then("the response should contain a list of books")
    public void theResponseShouldContainAListOfBooks() {
        allBooks.set(response.get().jsonPath().getList("$"));
        Assert.assertFalse(allBooks.get().isEmpty(), "Book list should not be empty");
    }

    @Then("every book should contain all required fields")
    public void everyBookShouldContainAllRequiredFields() {

        for (Map<String, Object> bookJson : allBooks.get()) {
            Integer id = (Integer) bookJson.get("id");
            String bookInfo = "Book JSON at index with id: " + id;

            Assert.assertTrue(bookJson.containsKey("id"), "Missing key 'id' in " + bookInfo);
            Assert.assertTrue(bookJson.containsKey("name"), "Missing key 'name' in " + bookInfo);
            Assert.assertTrue(bookJson.containsKey("author"), "Missing key 'author' in " + bookInfo);
            Assert.assertTrue(bookJson.containsKey("publication"), "Missing key 'publication' in " + bookInfo);
            Assert.assertTrue(bookJson.containsKey("category"), "Missing key 'category' in " + bookInfo);
            Assert.assertTrue(bookJson.containsKey("pages"), "Missing key 'pages' in " + bookInfo);
            Assert.assertTrue(bookJson.containsKey("price"), "Missing key 'price' in " + bookInfo);
        }

        LOGGER.info("Validated {} books - all contain required fields", allBooks.get().size());
    }

    @When("I get the created book by ID")
    public void iRequestTheCreatedBookById() {
        Integer idToRetrieve = bookIdForTest.get();
        Assert.assertNotNull(idToRetrieve, "No book ID available for retrieval");

        response.set(bookApiClient.getBookById(idToRetrieve));
    }

    @When("I get the created book by fake ID")
    public void iRequestTheCreatedBookByFakeId() {
        response.set(bookApiClient.getBookById(faker.getBigIntNumber()));
    }

    @When("I get the created book by invalid id format {string}")
    public void iRequestTheCreatedBookByInvalidIdFormat(String invalidId) {
        Response response1 = given()
                .pathParam("id", invalidId)
                .get(BOOK_BY_ID_ENDPOINT);

        response.set(response1);
    }

    @When("I create a new book")
    public void iCreateANewBook() {
        response.set(bookApiClient.createBook(bookRequest.get()));
        bookResponse.set(response.get().as(BookResponse.class));
        bookIdForTest.set(bookResponse.get().getId());

        TestHooks.getBookIdForCleanUp(bookIdForTest.get());
    }

    @When("I create a new book which should not be created")
    public void iCreateANewBookWhichShouldNotBeCreated() {
        response.set(bookApiClient.createBook(bookRequest.get()));
    }

    @Then("the response should contain the book details")
    public void theResponseShouldContainBookDetails() {

        Assert.assertNotNull(bookResponse.get().getId(), "Created book should have an ID");
        Assert.assertEquals(bookResponse.get().getName(), bookRequest.get().getName(), "Actual name: " + bookResponse.get().getName() + ", but expected: " + bookRequest.get().getName());
        Assert.assertEquals(bookResponse.get().getAuthor(), bookRequest.get().getAuthor(), "Actual author: " + bookResponse.get().getAuthor() + ", but expected: " + bookRequest.get().getAuthor());
        Assert.assertEquals(bookResponse.get().getPublication(), bookRequest.get().getPublication(), "Actual publication: " + bookResponse.get().getPublication() + ", but expected: " + bookRequest.get().getPublication());
        Assert.assertEquals(bookResponse.get().getCategory(), bookRequest.get().getCategory(), "Actual category: " + bookResponse.get().getCategory() + ", but expected: " + bookRequest.get().getCategory());
        Assert.assertEquals(bookResponse.get().getPages(), bookRequest.get().getPages(), "Actual pages count: " + bookResponse.get().getPages() + ", but expected: " + bookRequest.get().getPages());
        Assert.assertEquals(bookResponse.get().getPrice(), bookRequest.get().getPrice(), "Actual price: " + bookResponse.get().getPrice() + ", but expected: " + bookRequest.get().getPrice());

    }

    @Then("the response should contain the created short book details")
    public void theResponseShouldContainTheCreatedShortBookDetails() {

        Assert.assertNotNull(bookResponse.get().getId(), "Created book should have an ID");
        Assert.assertEquals(bookResponse.get().getName(), bookRequest.get().getName(), "Actual name: " + bookResponse.get().getName() + ", but expected: " + bookRequest.get().getName());
        Assert.assertEquals(bookResponse.get().getAuthor(), bookRequest.get().getAuthor(), "Actual author: " + bookResponse.get().getAuthor() + ", but expected: " + bookRequest.get().getAuthor());
        Assert.assertEquals(bookResponse.get().getPublication(), bookRequest.get().getPublication(), "Actual publication: " + bookResponse.get().getPublication() + ", but expected: " + bookRequest.get().getPublication());
        Assert.assertEquals(bookResponse.get().getCategory(), bookRequest.get().getCategory(), "Actual category: " + bookResponse.get().getCategory() + ", but expected: " + bookRequest.get().getCategory());
        Assert.assertEquals(bookResponse.get().getPages(), 0, "Actual pages count: " + bookResponse.get().getPages() + ", but expected 0");
        Assert.assertEquals(bookResponse.get().getPrice(), 0.0, "Actual price: " + bookResponse.get().getPrice() + ", but expected 0.0");

    }

    @Then("the book should be available when requested by ID")
    public void theBookShouldBeAvailableWhenRequestedById() {
        Response getResponse = bookApiClient.getBookById(bookResponse.get().getId());
        Assert.assertEquals(getResponse.getStatusCode(), 200);
        BookResponse retrievedBook = getResponse.as(BookResponse.class);
        Assert.assertEquals(retrievedBook.getId(), bookResponse.get().getId());

    }

    @When("I update the created book with the following details:")
    public void iUpdateTheCreatedBookWithTheFollowingDetails(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> data = rows.get(0);

        updateRequest.set(BookRequest.builder()
                .name(data.get("name"))
                .author(data.get("author"))
                .publication(data.get("publication"))
                .category(data.get("category"))
                .pages(Integer.parseInt(data.get("pages")))
                .price(Double.parseDouble(data.get("price")))
                .build());

        Integer idToUpdate = bookIdForTest.get();
        Assert.assertNotNull(idToUpdate, "No book ID available for update");

        response.set(bookApiClient.updateBook(idToUpdate, updateRequest.get()));
    }

    @When("I update the created book with missing required fields")
    public void iUpdateTheCreatedBookWithMissingRequiredFields() {

        updateRequest.set(BookRequest.builder()
                .publication(faker.getBookPublication())
                .category(faker.getBookCategory())
                .pages(faker.getPagesNumber())
                .price(faker.getPriceNumber())
                .build());

        Integer idToUpdate = bookIdForTest.get();
        Assert.assertNotNull(idToUpdate, "No book ID available for update");

        response.set(bookApiClient.updateBook(idToUpdate, updateRequest.get()));
    }


    @When("I update the created book with empty body")
    public void iUpdateTheCreatedBookWithEmptyBody() {

        updateRequest.set(BookRequest.builder()
                .build());

        Integer idToUpdate = bookIdForTest.get();
        Assert.assertNotNull(idToUpdate, "No book ID available for update");

        response.set(bookApiClient.updateBook(idToUpdate, updateRequest.get()));
    }

    @When("I update a book with fake id")
    public void iUpdateNonExistentBook() {
        updateRequest.set(BookRequest.builder()
                .name(faker.getBookName())
                .author(faker.getBookAuthor())
                .publication(faker.getBookPublication())
                .category(faker.getBookCategory())
                .pages(faker.getPagesNumber())
                .price(faker.getPriceNumber())
                .build());

        response.set(bookApiClient.updateBook(faker.getBigIntNumber(), updateRequest.get()));
    }

    @When("I update a book with invalid id format: {string}")
    public void iUpdateBookWithInvalidIdFormat(String invalidFormat) {
        updateRequest.set(BookRequest.builder()
                .name(faker.getBookName())
                .author(faker.getBookAuthor())
                .publication(faker.getBookPublication())
                .category(faker.getBookCategory())
                .pages(faker.getPagesNumber())
                .price(faker.getPriceNumber())
                .build());

        Response response1 = given()
                .pathParam("id", invalidFormat)
                .body(updateRequest.get())
                .put(BOOK_BY_ID_ENDPOINT);

        response.set(response1);
    }

    @Then("the book should be permanently updated with new values")
    public void theBookShouldBePermanentlyUpdatedWithNewValues() {
        Integer updatedId = bookIdForTest.get();
        Assert.assertNotNull(updatedId, "No book ID available to verify update");

        Response getResponse = bookApiClient.getBookById(updatedId);
        Assert.assertEquals(getResponse.getStatusCode(), 200);

        BookResponse retrievedBook = getResponse.as(BookResponse.class);
        Assert.assertEquals(retrievedBook.getName(), updateRequest.get().getName());
        Assert.assertEquals(retrievedBook.getAuthor(), updateRequest.get().getAuthor());
        Assert.assertEquals(retrievedBook.getCategory(), updateRequest.get().getCategory());
    }

    @When("I delete the created book")
    public void iDeleteTheCreatedBook() {
        Integer idToDelete = bookIdForTest.get();
        Assert.assertNotNull(idToDelete, "No book ID available for deletion");

        response.set(bookApiClient.deleteBook(idToDelete));
    }

    @When("I delete book with non-existent id")
    public void iDeleteBookWithNonExistentId() {
        int idToDelete = faker.getBigIntNumber();
        response.set(bookApiClient.deleteBook(idToDelete));
    }

    @When("I delete book with invalid id format: {string}")
    public void iDeleteBookWithInvalidIdFormat(String format) {
        Response response1 = given().pathParam("id", format).delete(BOOK_BY_ID_ENDPOINT);

        response.set(response1);
    }

    @Then("the deleted book should no longer be available")
    public void theDeletedBookShouldNoLongerBeAvailable() {
        Integer deletedId = bookIdForTest.get();
        Assert.assertNotNull(deletedId, "No book ID available to verify deletion");

        Response getResponse = bookApiClient.getBookById(deletedId);
        Assert.assertEquals(getResponse.getStatusCode(), 404, "Book with ID " + deletedId + " still exists after deletion");
    }
}
