package com.jwieczor.test_api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SubtypesTests {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api.magicthegathering.io/v1";
    }


    @Test
    public void testGetAllCards() {
        Response response =
                given()
                        .when()
                        .get("/subtypes")
                        .then()
                        .statusCode(200)
                        .extract().response();

        response.then().body("subtypes.size()", equalTo(500));
    }
}
