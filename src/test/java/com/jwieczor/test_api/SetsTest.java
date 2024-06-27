package com.jwieczor.test_api;

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
    public void testGetAllCards() {
        Response response =
            given()
                    .when()
                    .get("/sets")
                    .then()
                    .statusCode(200)
                    .extract().response();

        response.then().body("sets.size()", greaterThan(0));
    }

    @Test
    public void wrongMethod() {
        given()
                .when()
                .post("/sets")
                .then()
                .statusCode(405);
    }

//Poprawna paginacja
    @Test
    public void testPagination() {
        int page = 2;
        int pagesize = 10;

        Response response = RestAssured.given()
                .param("page", page)
                .param("pageSize", pagesize)
                .when()
                .get("/sets");

        response.then().statusCode(200);

        response.then().body("sets.size()", equalTo(pagesize));
    }
// za duzy numer strony
    @Test
    public void testPaginationInvalidPageNumber() {
        int page = 20000;
        int pagesize = 10;

        Response response = RestAssured.given()
                .param("page", page)
                .param("pageSize", pagesize)
                .when()
                .get("/sets");

        response.then().statusCode(200);

        response.then().body("sets.size()", equalTo(0));
    }

// zamiast liczby slowo
    @Test
    public void testPaginationStringInstedOfNumber() {
        String page = "hhahaha";
        int pagesize = 10;

        Response response = RestAssured.given()
                .param("page", page)
                .param("pageSize", pagesize)
                .when()
                .get("/sets");

        response.then().statusCode(400);

    }

// Wielkosc strony zero
    @Test
    public void testPaginationzeroAsPageSize() {
        Integer page = 1;
        int pagesize = 0;

        Response response = RestAssured.given()
                .param("page", page)
                .param("pageSize", pagesize)
                .when()
                .get("/sets");

        response.then().statusCode(200);
        response.then().body("sets.size()", equalTo(pagesize));

    }
// Duza wartosc wielkosci strony
    @Test
    public void testPaginationBigPageSize() {
        Integer page = 1;
        int pagesize = 1000000;

        Response response = RestAssured.given()
                .param("page", page)
                .param("pageSize", pagesize)
                .when()
                .get("/sets");

        response.then().statusCode(200);

    }
// Slowo zamiast liczby w wielkosci strony

    @Test
    public void testPaginationStringAsPageSize() {
        Integer page = 1;
        String pagesize = "hahahah";

        Response response = RestAssured.given()
                .param("page", page)
                .param("pageSize", pagesize)
                .when()
                .get("/sets");

        response.then().statusCode(400);

    }

    @Test
    public void testPaginationNumberOfPageIsNull() {
        Integer page = null;
        Integer pagesize = 20;

        Response response = RestAssured.given()
                .param("page", page)
                .param("pageSize", pagesize)
                .when()
                .get("/sets");

        response.then().statusCode(400);

    }




    @Test
    public void testDatabaseQuery() {
        String name = "Khans of Tarkir";

        Response response = RestAssured.given()
                .param("name", name)
                .when()
                .get("/sets");

        response.then().statusCode(200);

        response.then().body("sets.name", hasItem("Khans of Tarkir"));
    }

    @Test
    public void testDatabaseQuery4() {
        String name = "Khans of Tarkir";
        String block = "Khans of Tarkir";

        Response response = RestAssured.given()
                .param("name", name)
                .param("block", block)
                .when()
                .get("/sets");

        response.then().statusCode(200);

        response.then().body("sets.name", hasItem("Khans of Tarkir"));
        response.then().body("sets.block", hasItem("Khans of Tarkir"));
    }

    @Test
    public void testDatabaseQuery5() {
        String block = "Khans of Tarkir";

        Response response = RestAssured.given()
                .param("block", block)
                .when()
                .get("/sets");

        response.then().statusCode(200);

        response.then().body("sets.block", hasItem("Khans of Tarkir"));
    }

    @Test
    public void testDatabaseQuery2() {
        String name = null;
        String code = null;

        Response response = RestAssured.given()
                .param("name", name)
                .param("code", code)
                .when()
                .get("/sets");

        response.then().statusCode(200);
        response.then().body("size()", greaterThan(0));

    }

    @Test
    public void testDatabaseQuery3() {
        String name = "nazwak która nie istenije";
        String code = null;

        Response response = RestAssured.given()
                .param("name", name)
                .when()
                .get("/sets");

        response.then().statusCode(200);
        response.then().body("sets.size()", equalTo(0));

    }








}
