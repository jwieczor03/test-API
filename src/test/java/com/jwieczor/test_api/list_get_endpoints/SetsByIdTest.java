package com.jwieczor.test_api.list_get_endpoints;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SetsByIdTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api.magicthegathering.io/v1";
    }

    @Test
    public void getSetById() {
        Response response = given()
                .when()
                .get("/sets/ktk")
                .then()
                .statusCode(200)
                .extract().response();

        response.then().body("set.size()", greaterThan(0));
    }

    @Test
    public void getNonExistentSetByIdReturns404() {
        given()
                .when()
                .get("/sets/nonexistent")
                .then()
                .statusCode(404);
    }

    @Test
    public void getSetDetailsById() {
        given()
                .when()
                .get("/sets/ktk")
                .then()
                .statusCode(200)
                .body("set.code", equalTo("KTK"))
                .body("set.name", equalTo("Khans of Tarkir"))
                .body("set.type", equalTo("expansion"))
                .body("set.releaseDate", equalTo("2014-09-26"))
                .body("set.block", equalTo("Khans of Tarkir"))
                .body("set.onlineOnly", equalTo(false));
    }
}
