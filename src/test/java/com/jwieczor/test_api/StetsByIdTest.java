package com.jwieczor.test_api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class StetsByIdTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api.magicthegathering.io/v1";
    }

    @Test
    public void testGetAllCards() {
        Response response =
                given()
                        .when()
                        .get("/sets/ktk")
                        .then()
                        .statusCode(200)
                        .extract().response();

        response.then().body("set.size()", greaterThan(0));
    }

    @Test
    public void testGetAllCards1() {

                given()
                        .when()
                        .get("/sets/naPewnoNiemaTakiejNazwy")
                        .then()
                        .statusCode(404);
    }

    @Test
    public void testGetSetDetails() {

        given().
                when().
                get("/sets/ktk").
                then().
                assertThat().
                statusCode(200).
                body("set.code", equalTo("KTK")).
                body("set.name", equalTo("Khans of Tarkir")).
                body("set.type", equalTo("expansion")).
                body("set.releaseDate", equalTo("2014-09-26")).
                body("set.block", equalTo("Khans of Tarkir")).
                body("set.onlineOnly", equalTo(false));
    }

}
