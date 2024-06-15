package com.jwieczor.test_api;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;



public class cardsTests {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api.magicthegathering.io/v1";
    }

    @Test
    public void testGetAllCards() {
        given()
                .when()
                .get("/cards")
                .then()
                .statusCode(200)
                .header("Count", equalTo("100"));
    }

    @Test
    public void wrongMethod() {
        given()
                .when()
                .post("/cards")
                .then()
                .statusCode(405);
    }

    @Test
    public void checIfIsNotNull() {
        Response response =
            given()
                    .when()
                    .get("/cards")
                    .then()
                    .extract().response();
        response.then().body("size()", greaterThan(0));

    }

    @Test
    public void checkFirstRecord() {

        Response response = get("/cards");
        String name = response.jsonPath().getString("cards[0].name");
        String id =response.jsonPath().getString("cards[0].multiverseid");
        assertThat(name, equalTo("Ancestor's Chosen"));
        assertThat(id, equalTo("130550"));

    }









}




