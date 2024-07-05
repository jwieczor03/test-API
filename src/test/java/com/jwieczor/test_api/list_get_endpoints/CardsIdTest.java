package com.jwieczor.test_api.list_get_endpoints;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CardsIdTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api.magicthegathering.io/v1";
    }

    @Test
    public void getCardByMultiverseId() {
        Response response = given()
                .when()
                .get("/cards/130550")
                .then()
                .statusCode(200)
                .extract().response();
        response.then().body("size()", greaterThan(0));
    }

    @Test
    public void getCardByUUID() {
        Response response = given()
                .when()
                .get("/cards/5f8287b1-5bb6-5f4c-ad17-316a40d5bb0c")
                .then()
                .statusCode(200)
                .extract().response();
        response.then().body("size()", greaterThan(0));
    }

    @Test
    public void getNonExistentCardReturns404() {
        given()
                .when()
                .get("/cards/0")
                .then()
                .statusCode(404);
    }

    @Test
    public void getInvalidPathVariableReturns404() {
        given()
                .when()
                .get("/cards/invalidId")
                .then()
                .statusCode(404);
    }

    @Test
    public void postCardReturns405() {
        given()
                .when()
                .post("/cards/1")
                .then()
                .statusCode(405);
    }

    @Test
    public void checkCardDetailsByMultiverseId() {
        Response response = get("/cards/130550");

        String name = response.jsonPath().getString("card.name");
        String multiverseid = response.jsonPath().getString("card.multiverseid");
        String id = response.jsonPath().getString("card.id");

        assertThat(name, equalTo("Ancestor's Chosen"));
        assertThat(multiverseid, equalTo("130550"));
        assertThat(id, equalTo("5f8287b1-5bb6-5f4c-ad17-316a40d5bb0c"));
    }

    @Test
    public void compareCardDetailsByMultiverseIdAndUUID() {
        Response responseByMultiverseId = get("/cards/130550");
        Response responseByUUID = get("/cards/5f8287b1-5bb6-5f4c-ad17-316a40d5bb0c");

        String nameByMultiverseId = responseByMultiverseId.jsonPath().getString("card.name");
        String multiverseidByMultiverseId = responseByMultiverseId.jsonPath().getString("card.multiverseid");
        String idByMultiverseId = responseByMultiverseId.jsonPath().getString("card.id");

        String nameByUUID = responseByUUID.jsonPath().getString("card.name");
        String multiverseidByUUID = responseByUUID.jsonPath().getString("card.multiverseid");
        String idByUUID = responseByUUID.jsonPath().getString("card.id");

        assertThat(nameByMultiverseId, equalTo(nameByUUID));
        assertThat(multiverseidByMultiverseId, equalTo(multiverseidByUUID));
        assertThat(idByMultiverseId, equalTo(idByUUID));
    }
}