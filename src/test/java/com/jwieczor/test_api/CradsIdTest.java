package com.jwieczor.test_api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CradsIdTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api.magicthegathering.io/v1";
    }

    @Test
    public void testGetAllCards() {

        Response response =
            given()
                    .when()
                        .get("/cards/130550")
                    .then()
                        .statusCode(200)
                        .extract().response();
        response.then().body("size()", greaterThan(0));

    }

    @Test
    public void id() {

        Response response =
                given()
                        .when()
                        .get("/cards/5f8287b1-5bb6-5f4c-ad17-316a40d5bb0c")
                        .then()
                        .statusCode(200)
                        .extract().response();
        response.then().body("size()", greaterThan(0));

    }

    @Test
    public void idWhichDoesNotExist(){

        given()
                .when()
                    .get("/cards/0")
                .then()
                    .statusCode(404);
    }

    @Test
    public void invalidPathVariable(){

        given()
                .when()
                .get("/cards/hahahhah")
                .then()
                .statusCode(404);
    }

    @Test
    public void wrongMethod() {
        given()
                .when()
                .post("/cards/1")
                .then()
                .statusCode(405);
    }

    @Test
    public void checkFirstRecord() {

        Response response = get("/cards/130550");

        String name = response.jsonPath().getString("card.name");
        String multiverseid = response.jsonPath().getString("card.multiverseid");
        String id = response.jsonPath().getString("card.id");

        assertThat(name, equalTo("Ancestor's Chosen"));
        assertThat(multiverseid, equalTo("130550"));
        assertThat(id, equalTo("5f8287b1-5bb6-5f4c-ad17-316a40d5bb0c"));


    }

    @Test
    public void iTheSame(){

        Response responseMultiuniversalId = get("/cards/130550");
        Response responseId = get("/cards/5f8287b1-5bb6-5f4c-ad17-316a40d5bb0c");

        String nameMulti = responseMultiuniversalId.jsonPath().getString("card.name");
        String multiverseidMulti = responseMultiuniversalId.jsonPath().getString("card.multiverseid");
        String idMulti = responseMultiuniversalId.jsonPath().getString("card.id");

        String nameId = responseId.jsonPath().getString("card.name");
        String multiverseidId = responseId.jsonPath().getString("card.multiverseid");
        String idId = responseId.jsonPath().getString("card.id");

        assertThat(nameMulti, equalTo(nameId));
        assertThat(multiverseidMulti, equalTo(multiverseidId));
        assertThat(idMulti, equalTo(idId));

    }




}
