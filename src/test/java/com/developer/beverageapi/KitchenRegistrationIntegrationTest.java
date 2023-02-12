package com.developer.beverageapi;

import com.developer.beverageapi.core.io.Base64ProtocolResolver;
import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.KitchenNotFoundException;
import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import com.developer.beverageapi.domain.service.KitchenRegistrationService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import util.DatabaseCleaner;
import util.ResourceUtils;

import javax.validation.ConstraintViolationException;

import static com.google.common.base.Predicates.equalTo;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@ContextConfiguration(initializers = Base64ProtocolResolver.class) // It Calls the convertor base64
public class KitchenRegistrationIntegrationTest {

	private static final int KITCHEN_ID_THAT_DOES_NOT_EXIST = 100;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private RepositoryKitchen repositoryKitchen;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private MockMvc mockMvc;

	private Kitchen kitchen;
	private int quantityOfKitchensRecorded;
	private String correctKitchenJson;

	@BeforeEach
	public void setUp() {
		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
		RestAssuredMockMvc.mockMvc(mockMvc);
		RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssuredMockMvc.basePath = "/v1/cozinhas";

		correctKitchenJson = ResourceUtils.getContentFromResource("/json/correct/kitchen.json");

		databaseCleaner.clearTables();
		prepareData();
	}

	@Test
	@WithMockUser(
			username = "zoro.sword@onepice.com",
			authorities = {
					"SCOPE_READ",
					"SCOPE_WRITE",
					"EDIT_KITCHENS"
			}
	)
	public void mustReturnStatus200_WhenConsultingKitchens() {
		RestAssuredMockMvc
				.given()
				.accept(ContentType.JSON)
				.when()
				.get()
				.then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	@WithMockUser(
			username = "zoro.sword@onepice.com",
			authorities = {
					"SCOPE_READ",
					"SCOPE_WRITE",
					"EDIT_KITCHENS"
			}
	)
	public void mustReturnCorrectQuantityOfKitchens_WhenConsultingKitchens() {
		RestAssuredMockMvc
				.given()
				.accept(ContentType.JSON)
				.when()
				.get()
				.then()
				.body("_embedded.kitchens", hasSize(quantityOfKitchensRecorded));
		//_embedded.kitchens since it is being used HATEOAS
	}

	@Test
	@WithMockUser(
			username = "zoro.sword@onepice.com",
			authorities = {
					"SCOPE_READ",
					"SCOPE_WRITE",
					"EDIT_KITCHENS"
			}
	)
	public void mustReturnStatus201_WhenRecordingKitchen() {
		RestAssuredMockMvc
				.given()
				.body(correctKitchenJson)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.when()
				.post()
				.then()
				.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	@WithMockUser(
			username = "zoro.sword@onepice.com",
			authorities = {
					"SCOPE_READ",
					"SCOPE_WRITE",
					"EDIT_KITCHENS"
			}
	)
	public void mustReturnAnswerAndStatusCorrect_WhenConsultingAKitchenThatExists() {
		RestAssuredMockMvc
				.given()
				.accept(ContentType.JSON)
				.when()
				.get(kitchen.getId().toString())
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("name", Matchers.equalTo(kitchen.getName()));
	}

	@Test
	@WithMockUser(
			username = "zoro.sword@onepice.com",
			authorities = {
					"SCOPE_READ",
					"SCOPE_WRITE",
					"EDIT_KITCHENS"
			}
	)
	public void mustReturnStatus404_WhenConsultingAKitchenThatDoesNotExist() {
		RestAssuredMockMvc
				.given()
				.accept(ContentType.JSON)
				.when()
				.get(String.valueOf(KITCHEN_ID_THAT_DOES_NOT_EXIST))
				.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	private void prepareData() {
		Kitchen brazilianKitchen = new Kitchen();
		brazilianKitchen.setName("Brazilian");
		repositoryKitchen.save(brazilianKitchen);

		Kitchen americanKitchen = new Kitchen();
		americanKitchen.setName("American");
		repositoryKitchen.save(americanKitchen);;

		quantityOfKitchensRecorded = (int) repositoryKitchen.count();
	}
}

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class KitchenRegistrationIntegrationTest {
//
//	@Autowired
//	private KitchenRegistrationService kitchenRegistration;
//
//	@Test
//	public void mustRegisterKitchenSuccessfully() {
//
//		//scene
//		Kitchen newKitchen = new Kitchen();
//		newKitchen.setName("Chinese");
//
//		//action
//		newKitchen = kitchenRegistration.add(newKitchen);
//
//		//validation
//		Assertions.assertThat(newKitchen).isNotNull();
//		Assertions.assertThat(newKitchen.getId()).isNotNull();
//	}
//
//	@Test(expected = ConstraintViolationException.class)
//	public void mustFailRegisteringKitchenWithoutName() {
//		Kitchen newKitchen = new Kitchen();
//		newKitchen.setName(null);
//
//		newKitchen = kitchenRegistration.add(newKitchen);
//	}
//
//	/**
//	 * > No tests found for given includes: [com.developer.beverageapi.KitchenRegistrationIntegrationTest.mustRegisterKitchenSuccessfully](filter.includeTestsMatching)
//	 *
//	 * Go to Preferences -> Build, Execution, Deployment -> Gradle -> change "Run tests using" to "IntelliJ IDEA".
//	 * Run your test.
//	 * Go again to Preferences -> Build, Execution, Deployment -> Gradle -> change "Run tests using" to "Gradle (default)".
//	 * Keep running your test, it is working now.
//	 */
//
//	@Test(expected = EntityInUseException.class)
//	public void mustFail_WhenDeletingKitchenInUse() {
//		kitchenRegistration.remove(1L);
//	}
//
//	@Test(expected = KitchenNotFoundException.class)
//	public void mustFail_WhenDeletingKitchenThatDoesNotExists() {
//		kitchenRegistration.remove(100L);
//	}
//}
