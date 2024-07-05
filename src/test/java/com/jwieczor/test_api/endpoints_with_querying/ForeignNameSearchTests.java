package com.jwieczor.test_api.endpoints_with_querying;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
public class ForeignNameSearchTests {


    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api.magicthegathering.io/v1";
    }

    // Pozytywne scenariusze
    @Test
    public void testGetCardsByForeignNamePartialMatch() {
        String partialName = "Arcángel";
        String language = "spanish";

        given()
                .queryParam("name", partialName)
                .queryParam("language", language)
                .when()
                .get("/cards")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("cards", not(empty()))
                .body("cards.foreignNames.findAll { it.language == 'Spanish' }.name", everyItem(containsStringIgnoringCase(partialName)));
    }

    @Test
    public void testGetCardsByForeignNameExactMatch() {
        String exactName = "Elegido de la Antepasada";
        String language = "spanish";

        given()
                .queryParam("name", "\"" + exactName + "\"")
                .queryParam("language", language)
                .when()
                .get("/cards")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("cards", not(empty()))
                .body("cards.foreignNames.findAll { it.language == 'Spanish' }.name", everyItem(equalTo(exactName)));
    }

    // Negatywne scenariusze
    @Test
    public void testGetCardsByForeignNameNoMatch() {
        String nonExistentName = "NonExistentCardName";
        String language = "spanish";

        given()
                .queryParam("name", nonExistentName)
                .queryParam("language", language)
                .when()
                .get("/cards")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("cards", empty());
    }

    @Test
    public void testGetCardsByForeignNameInvalidQueryParam() {
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
