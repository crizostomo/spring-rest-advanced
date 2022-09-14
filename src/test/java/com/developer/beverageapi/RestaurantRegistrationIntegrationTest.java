package com.developer.beverageapi;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;

import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import com.developer.beverageapi.domain.repository.RepositoryRestaurant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import util.DatabaseCleaner;
import util.ResourceUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.yml")
public class RestaurantRegistrationIntegrationTest {

    private static final String BUSINESS_VIOLATION_RULE_PROBLEM_TYPE = "Violation of business rule";

    private static final String INVALID_DATA_PROBLEM_TITLE = "Invalid Data";

    private static final int RESTAURANT_ID_THAT_DOES_NOT_EXIST = 100;

    @LocalServerPort
    private int port;

//    @Autowired
//    private DatabaseCleaner databaseCleaner;

    @Autowired
    private RepositoryKitchen repositoryKitchen;

    @Autowired
    private RepositoryRestaurant repositoryRestaurant;

    private String correctRestaurantJson;
    private String restaurantWithoutDeliveryJson;
    private String restaurantWithoutKitchenJson;
    private String restaurantWithKitchenThatDoesNotExistJson;

    private Restaurant burgerTopRestaurant;

    @Before
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurants";

        correctRestaurantJson = ResourceUtils.getContentFromResource(
                "/json/correct/new-york-barbecue-restaurant.json");

        restaurantWithoutDeliveryJson = ResourceUtils.getContentFromResource(
                "/json/incorrect/new-york-barbecue-restaurant-without-delivery.json");

        restaurantWithoutKitchenJson = ResourceUtils.getContentFromResource(
                "/json/incorrect/new-york-barbecue-kitchen-without-kitchen.json");

        restaurantWithKitchenThatDoesNotExistJson = ResourceUtils.getContentFromResource(
                "/json/incorrect/new-york-barbecue-restaurant-with-kitchen-that-does-not-exist.json");

//        databaseCleaner.clearTables();
        prepareData();
    }

    @Test
    public void mustReturnStatus200_WhenConsultingRestaurants() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void mustReturnStatus201_WhenRecordingRestaurant() {
        given()
                .body(correctRestaurantJson)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void mustReturnStatus400_WhenRecordingRestaurantWithoutDelivery() {
        given()
                .body(restaurantWithoutDeliveryJson)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(INVALID_DATA_PROBLEM_TITLE));
    }

    @Test
    public void mustReturnStatus400_WhenRecordingRestaurantWithoutKitchen() {
        given()
                .body(restaurantWithoutKitchenJson)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(INVALID_DATA_PROBLEM_TITLE));
    }

    @Test
    public void mustReturnStatus400_WhenRecordingRestaurantWithKitchenThatDoesNotExist() {
        given()
                .body(restaurantWithKitchenThatDoesNotExistJson)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(BUSINESS_VIOLATION_RULE_PROBLEM_TYPE));
    }

    @Test
    public void mustReturnAnswerAndStatusCorrectly_WhenConsultingExistingRestaurant() {
        given()
                .pathParam("restaurantId", burgerTopRestaurant.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{restaurantId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(burgerTopRestaurant.getName()));
    }

    @Test
    public void mustReturnStatus404_WhenConsultingRestaurantThatDoesNotExist() {
        given()
                .pathParam("restaurantId", RESTAURANT_ID_THAT_DOES_NOT_EXIST)
                .accept(ContentType.JSON)
                .when()
                .get("/{restaurantId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepareData() {
        Kitchen brazilianKitchen = new Kitchen();
        brazilianKitchen.setName("Brazilian");
        repositoryKitchen.save(brazilianKitchen);

        Kitchen americanKitchen = new Kitchen();
        americanKitchen.setName("American");
        repositoryKitchen.save(americanKitchen);

        burgerTopRestaurant = new Restaurant();
        burgerTopRestaurant.setName("Top Burger");
        burgerTopRestaurant.setDelivery(new BigDecimal(10));
        burgerTopRestaurant.setKitchen(americanKitchen);
        repositoryRestaurant.save(burgerTopRestaurant);

        Restaurant mineiraFoodRestaurant = new Restaurant();
        mineiraFoodRestaurant.setName("Mineira Food");
        mineiraFoodRestaurant.setDelivery(new BigDecimal(10));
        mineiraFoodRestaurant.setKitchen(brazilianKitchen);
        repositoryRestaurant.save(mineiraFoodRestaurant);
    }
}
