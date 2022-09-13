package com.developer.beverageapi;

import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import util.DatabaseCleaner;
import util.ResourceUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.yml")
public class APIKitchenRegistrationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private Flyway flyway;

//    @Autowired
//    private DatabaseCleaner databaseCleaner;

    @Autowired
    private RepositoryKitchen repositoryKitchen;

    private Kitchen americanKitchen;
    private int recordedKitchenQuantity;
    private String correctJsonIndianKitchen;


    /**
     * It was used the Flyway because if the test 'mustReturnStatus201_WhenRecordingKitchen', is executed first
     * the test 'mustContain3Kitchens_WhenConsultingKitchens' will fail because it will be expecting 3 kitchens
     * instead of 4 (because one will be persisted after the test 'mustReturnStatus201_WhenRecordingKitchen')
     * Then, the flyway instance was used in the 'setUp' class since this class is started always before each class
     */

    @Before
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/kitchens";

        correctJsonIndianKitchen = ResourceUtils.getContentFromResource(
                "/json/correct/indian-kitchen.json");

//        databaseCleaner.clearTables();
//        prepareData();
        flyway.migrate();
    }

    @Test
    public void mustReturnStatus200_WhenConsultingKitchens() {
        RestAssured.given()
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
    public void mustContainTheRightQuantityOfKitchens_WhenConsultingKitchens() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", Matchers.hasSize(recordedKitchenQuantity))
                .body("name", Matchers.hasItems("Japanese", "Indian"));
    }

    @Test
    public void mustReturnStatus201_WhenRecordingKitchen() {
        RestAssured.given()
//                .body("{ \"name\": \"Indian\" }")
                .body(correctJsonIndianKitchen)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(201);
    }

    @Test
    public void mustReturnAnswerAndStatusCorrectly_WhenConsultingExistentKitchen() {
        RestAssured.given()
                .pathParam("kitchenId", americanKitchen.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{kitchenId}")
                .then()
                .statusCode(200)
                .body("name", Matchers.equalTo(americanKitchen.getName()));
    }

    @Test
    public void mustReturnStatus404_WhenConsultingAKitchenThatDoesNotExist() {
        RestAssured.given()
                .pathParam("kitchenId", 100)
                .accept(ContentType.JSON)
                .when()
                .get("/{kitchenId}")
                .then()
                .statusCode(404);
    }

    private void prepareData() {
        Kitchen kitchen1 = new Kitchen();
        kitchen1.setName("Argentinian");
        repositoryKitchen.save(kitchen1);

        Kitchen kitchen2 = new Kitchen();
        kitchen2.setName("American");
        repositoryKitchen.save(kitchen2);

        Kitchen kitchen3 = new Kitchen();
        kitchen3.setName("Peruvian");
        repositoryKitchen.save(kitchen3);

        recordedKitchenQuantity = (int) repositoryKitchen.count();
    }
}
