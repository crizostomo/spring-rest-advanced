package com.developer.beverageapi;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class APIKitchenRegistrationIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    public void mustReturnStatus200_WhenConsultingKitchens() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.given()
                .basePath("/kitchens")
                .port(port)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200);
    }
    /**
     * To Solve the problem: > No tests found for given includes: [com.developer.beverageapi.KitchenRegistrationIntegrationTest.mustRegisterKitchenSuccessfully](filter.includeTestsMatching)
     *
     * I had to use the: testImplementation group: 'io.rest-assured', name: 'rest-assured', version: '4.0.0'
     *
     * Instead of: testImplementation group: 'io.rest-assured', name: 'rest-assured', version: '5.2.0'
     */


    @Test
    public void mustContain3Kitchens_WhenConsultingKitchens() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.given()
                .basePath("/kitchens")
                .port(port)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", Matchers.hasSize(3))
                .body("name", Matchers.hasItems("Japanese", "Indian"));
    }
}
