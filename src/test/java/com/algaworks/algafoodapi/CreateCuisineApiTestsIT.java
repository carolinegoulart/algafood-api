package com.algaworks.algafoodapi;

import com.algaworks.algafoodapi.domain.model.Cuisine;
import com.algaworks.algafoodapi.domain.repository.CuisineRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import com.algaworks.algafoodapi.util.DatabaseCleaner;

import static com.algaworks.algafoodapi.util.ResourceUtils.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CreateCuisineApiTestsIT {

    private static final int INVALID_CUISINE_ID = 100;

    private int cuisinesQuantity;
    private String jsonChineseCuisine;
    private Cuisine americanCuisine;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CuisineRepository repository;

    @BeforeEach
    void setUp() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cuisines";

        jsonChineseCuisine = getContentFromResource("/json/expected/chinese-cuisine.json");

        databaseCleaner.clearTables();
        prepareData();
    }

    void prepareData() {
        Cuisine thailandCuisine = new Cuisine();
        thailandCuisine.setName("Tailandesa");
        repository.save(thailandCuisine);

        americanCuisine = new Cuisine();
        americanCuisine.setName("Americana");
        repository.save(americanCuisine);

        cuisinesQuantity = (int) repository.count();
    }

    @Test
    void shouldReturnHttpStatus200_WhenGetCuisines() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void shouldReturn2Cuisines_WhenGetCuisines() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("", hasSize(cuisinesQuantity));
    }

    @Test
    void shouldReturnHttpStatus201_WhenCreatingNewCuisine() {
        given()
            .body(jsonChineseCuisine)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void shouldReturnHttpStatus200AndBody_WhenGettingCuisineById() {
        given()
            .pathParam("cuisineId", americanCuisine.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{cuisineId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("name", equalTo("Americana"));
    }

    @Test
    void shouldReturnHttpStatus404_WhenGettingInvalidCuisineById() {
        given()
            .pathParam("cuisineId", INVALID_CUISINE_ID)
            .accept(ContentType.JSON)
        .when()
            .get("/{cuisineId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

}