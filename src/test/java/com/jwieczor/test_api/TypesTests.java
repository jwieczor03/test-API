package com.jwieczor.test_api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class TypesTests {


    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api.magicthegathering.io/v1";
    }


    @Test
    public void testGetAllCards() {
        Response response =
                given()
                        .when()
                        .get("/types")
                        .then()
                        .statusCode(200)
                        .extract().response();

        response.then().body("types.size()", equalTo(27));
    }
}
