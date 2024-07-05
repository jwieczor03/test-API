package com.jwieczor.test_api.endpoints_with_querying;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CardsTests {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api.magicthegathering.io/v1";
    }

    @Test
    public void getAllCardsReturns200AndCorrectCount() {
        given()
                .when()
                .get("/cards")
                .then()
                .statusCode(200)
                .header("Count", equalTo("100"));
    }

    @Test
    public void postCardsReturns405() {
        given()
                .when()
                .post("/cards")
                .then()
                .statusCode(405);
    }

    @Test
    public void getAllCardsResponseNotNull() {
        Response response = given()
                .when()
                .get("/cards")
                .then()
                .extract().response();
        response.then().body("size()", greaterThan(0));
    }

    @Test
    public void firstCardHasExpectedNameAndId() {
        Response response = get("/cards");
        String name = response.jsonPath().getString("cards[0].name");
        String id = response.jsonPath().getString("cards[0].multiverseid");
        assertThat(name, equalTo("Ancestor's Chosen"));
        assertThat(id, equalTo("130550"));
    }
}
