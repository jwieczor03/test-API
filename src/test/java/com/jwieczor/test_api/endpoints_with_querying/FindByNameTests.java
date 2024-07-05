package com.jwieczor.test_api.endpoints_with_querying;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class FindByNameTests {


    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api.magicthegathering.io/v1";
    }

    // Pozytywne scenariusze
    @Test
    public void testGetCardsByNamePartialMatch() {
        String partialName = "avacyn";

        given()
                .queryParam("name", partialName)
                .when()
                .get("/cards")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("cards", not(empty()))
                .body("cards.name", everyItem(containsStringIgnoringCase(partialName)));
    }

    @Test
    public void testGetCardsByNameExactMatch() {
        String exactName = "Avacyn, Angel of Hope";

        given()
                .queryParam("name", "\"" + exactName + "\"")
                .when()
                .get("/cards")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("cards", not(empty()))
                .body("cards.name", everyItem(equalTo(exactName)));
    }

    @Test
    public void testGetCardsByNameNoMatch() {
        String nonExistentName = "NonExistentCardName";

        given()
                .queryParam("name", nonExistentName)
                .when()
                .get("/cards")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("cards", empty());
    }

    @Test
    public void testGetCardsByNameInvalidQueryParam() {
        given()
                .queryParam("invalidParam", "someValue")
                .when()
                .get("/cards")
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("status", equalTo(400))
                .body("error", not(emptyOrNullString()));
    }

}
