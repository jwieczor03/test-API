package com.jwieczor.test_api.list_get_endpoints;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class FormatsTests {


    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api.magicthegathering.io/v1";
    }



    @Test
    public void testGetAllFormats() {
        Response response =
                given()
                        .when()
                        .get("/formats")
                        .then()
                        .statusCode(200)
                        .extract().response();

        response.then().body("formats.size()", equalTo(23));
    }
}
