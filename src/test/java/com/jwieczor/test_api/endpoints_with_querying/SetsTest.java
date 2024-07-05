package com.jwieczor.test_api.endpoints_with_querying;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SetsTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api.magicthegathering.io/v1";
    }

    @Test
    public void getAllSets() {
        Response response = given()
                .when()
                .get("/sets")
                .then()
                .statusCode(200)
                .extract().response();

        response.then().body("sets.size()", greaterThan(0));
    }

    @Test
    public void postSetsReturns405() {
        given()
                .when()
                .post("/sets")
                .then()
                .statusCode(405);
    }

    @Test
    public void validPagination() {
        int page = 2;
        int pageSize = 10;

        Response response = given()
                .param("page", page)
                .param("pageSize", pageSize)
                .when()
                .get("/sets");

        response.then().statusCode(200);
        response.then().body("sets.size()", equalTo(pageSize));
    }

    @Test
    public void paginationWithInvalidPageNumber() {
        int page = 20000;
        int pageSize = 10;

        Response response = given()
                .param("page", page)
                .param("pageSize", pageSize)
                .when()
                .get("/sets");

        response.then().statusCode(200);
        response.then().body("sets.size()", equalTo(0));
    }

    @Test
    public void paginationWithStringAsPageNumber() {
        String page = "invalid";
        int pageSize = 10;

        Response response = given()
                .param("page", page)
                .param("pageSize", pageSize)
                .when()
                .get("/sets");

        response.then().statusCode(400);
    }

    @Test
    public void paginationWithZeroPageSize() {
        int page = 1;
        int pageSize = 0;

        Response response = given()
                .param("page", page)
                .param("pageSize", pageSize)
                .when()
                .get("/sets");

        response.then().statusCode(200);
        response.then().body("sets.size()", equalTo(pageSize));
    }

    @Test
    public void paginationWithLargePageSize() {
        int page = 1;
        int pageSize = 1000000;

        Response response = given()
                .param("page", page)
                .param("pageSize", pageSize)
                .when()
                .get("/sets");

        response.then().statusCode(200);
    }

    @Test
    public void paginationWithStringAsPageSize() {
        int page = 1;
        String pageSize = "invalid";

        Response response = given()
                .param("page", page)
                .param("pageSize", pageSize)
                .when()
                .get("/sets");

        response.then().statusCode(400);
    }

    @Test
    public void paginationWithNullPageNumber() {
        Integer page = null;
        int pageSize = 20;

        Response response = given()
                .param("page", page)
                .param("pageSize", pageSize)
                .when()
                .get("/sets");

        response.then().statusCode(400);
    }

    @Test
    public void searchSetByName() {
        String name = "Khans of Tarkir";

        Response response = given()
                .param("name", name)
                .when()
                .get("/sets");

        response.then().statusCode(200);
        response.then().body("sets.name", hasItem(name));
    }

    @Test
    public void searchSetByNameAndBlock() {
        String name = "Khans of Tarkir";
        String block = "Khans of Tarkir";

        Response response = given()
                .param("name", name)
                .param("block", block)
                .when()
                .get("/sets");

        response.then().statusCode(200);
        response.then().body("sets.name", hasItem(name));
        response.then().body("sets.block", hasItem(block));
    }

    @Test
    public void searchSetByBlock() {
        String block = "Khans of Tarkir";

        Response response = given()
                .param("block", block)
                .when()
                .get("/sets");

        response.then().statusCode(200);
        response.then().body("sets.block", hasItem(block));
    }

    @Test
    public void searchSetWithNullNameAndCode() {
        String name = null;
        String code = null;

        Response response = given()
                .param("name", name)
                .param("code", code)
                .when()
                .get("/sets");

        response.then().statusCode(200);
        response.then().body("sets.size()", greaterThan(0));
    }

    @Test
    public void searchNonExistentSetName() {
        String name = "nonexistent set name";

        Response response = given()
                .param("name", name)
                .when()
                .get("/sets");

        response.then().statusCode(200);
        response.then().body("sets.size()", equalTo(0));
    }
}
