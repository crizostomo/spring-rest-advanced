package com.developer.beverageapi;

import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.service.KitchenRegistrationService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KitchenRegistrationIntegrationTest {

	@Autowired
	private KitchenRegistrationService kitchenRegistration;

	@Test
	public void mustRegisterKitchenSuccessfully() {

		//scene
		Kitchen newKitchen = new Kitchen();
		newKitchen.setName("Chinese");

		//action
		newKitchen = kitchenRegistration.add(newKitchen);

		//validation
		Assertions.assertThat(newKitchen).isNotNull();
		Assertions.assertThat(newKitchen.getId()).isNotNull();
	}

	@Test(expected = ConstraintViolationException.class)
	public void mustFailRegisteringKitchenWithoutName() {
		Kitchen newKitchen = new Kitchen();
		newKitchen.setName(null);

		newKitchen = kitchenRegistration.add(newKitchen);
	}

	/**
	 * > No tests found for given includes: [com.developer.beverageapi.KitchenRegistrationIntegrationTest.mustRegisterKitchenSuccessfully](filter.includeTestsMatching)
	 *
	 * Go to Preferences -> Build, Execution, Deployment -> Gradle -> change "Run tests using" to "IntelliJ IDEA".
	 * Run your test.
	 * Go again to Preferences -> Build, Execution, Deployment -> Gradle -> change "Run tests using" to "Gradle (default)".
	 * Keep running your test, it is working now.
	 */
}
