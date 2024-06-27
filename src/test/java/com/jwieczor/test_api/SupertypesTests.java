package com.jwieczor.test_api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SupertypesTests {


    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api.magicthegathering.io/v1";
    }


    @Test
    public void testGetAllCards() {
        Response response =
                given()
                        .when()
                        .get("/supertypes")
                        .then()
                        .statusCode(200)
                        .extract().response();

        response.then().body("supertypes.size()", equalTo(6));
    }
}
