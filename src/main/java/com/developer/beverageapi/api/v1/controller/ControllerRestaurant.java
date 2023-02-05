package com.developer.beverageapi.api.v1.controller;

import com.developer.beverageapi.api.v1.assembler.RestaurantBasicModelAssembler;
import com.developer.beverageapi.api.v1.assembler.RestaurantInputDismantle;
import com.developer.beverageapi.api.v1.assembler.RestaurantModelAssembler;
import com.developer.beverageapi.api.v1.assembler.RestaurantSummaryModelAssembler;
import com.developer.beverageapi.api.v1.model.RestaurantModel;
import com.developer.beverageapi.api.v1.model.input.RestaurantInput;
import com.developer.beverageapi.api.v1.swaggerapi.controller.ControllerRestaurantOpenApi;
import com.developer.beverageapi.core.security.CheckSecurity;
import com.developer.beverageapi.domain.exception.BusinessException;
import com.developer.beverageapi.domain.exception.CityNotFoundException;
import com.developer.beverageapi.domain.exception.KitchenNotFoundException;
import com.developer.beverageapi.domain.exception.RestaurantNotFoundException;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import com.developer.beverageapi.domain.repository.RepositoryRestaurant;
import com.developer.beverageapi.domain.service.RestaurantRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerRestaurant implements ControllerRestaurantOpenApi {

    @Autowired
    private RepositoryRestaurant repositoryRestaurant;

    @Autowired
    private RestaurantRegistrationService registrationRestaurant;

    @Autowired
    private RepositoryKitchen repositoryKitchen;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private RestaurantModelAssembler restaurantModelAssembler;

    @Autowired
    private RestaurantInputDismantle restaurantInputDismantle;

    @Autowired
    private RestaurantBasicModelAssembler restaurantBasicModelAssembler;

    @Autowired
    private RestaurantSummaryModelAssembler restaurantSummaryModelAssembler;

    //    @ApiOperation(value = "Restaurant Summary List", response = BasicRestaurantModelSwaggerApi.class)
    @CheckSecurity.Restaurants.AllowedToConsult
    @GetMapping
    public CollectionModel<RestaurantModel> list() {
        return restaurantModelAssembler.toCollectionModel(repositoryRestaurant.findAll());
    }

//    @ApiOperation(value = "Restaurant Summary List", hidden = true)
//    @JsonView(RestaurantView.Summary.class)

    @CheckSecurity.Restaurants.AllowedToConsult
    @GetMapping(params = "projection=summary")
    public CollectionModel<RestaurantModel> listSummary() {
        return list();
    }

    //	@GetMapping
//	public MappingJacksonValue list(@RequestParam(required = false) String projection) {
//		List<Restaurant> restaurants = repositoryRestaurant.findAll();
//		List<RestaurantModel> restaurantsModel = restaurantModelAssembler.toCollectionModel(restaurants);
//
//		MappingJacksonValue restaurantsWrapper = new MappingJacksonValue(restaurantsModel);
//
//		restaurantsWrapper.setSerializationView(RestaurantView.Summary.class);
//
//		if ("only-name".equals(projection)) {
//			restaurantsWrapper.setSerializationView(RestaurantView.OnlyName.class);
//		} else if ("complete".equals(projection)) {
//			restaurantsWrapper.setSerializationView(null);
//		}
//
//		return restaurantsWrapper;
//	}

    @CheckSecurity.Restaurants.AllowedToConsult
    @GetMapping("/{restaurantId}")
    public RestaurantModel search(@PathVariable Long restaurantId) {
        Restaurant restaurant = registrationRestaurant.searchOrFail(restaurantId);

        RestaurantModel restaurantModel = restaurantModelAssembler.toModel(restaurant);

        return restaurantModelAssembler.toModel(restaurant);
    }

    @CheckSecurity.Restaurants.AllowedToEdit
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantModel add(@RequestBody @Valid RestaurantInput restaurantInput) {
        try {
            Restaurant restaurant = restaurantInputDismantle.toDomainObject(restaurantInput);

            return restaurantModelAssembler.toModel(registrationRestaurant.add(restaurant));
        } catch (KitchenNotFoundException | CityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @CheckSecurity.Restaurants.AllowedToEdit
    @PutMapping("/{restaurantId}")
    public RestaurantModel update(@PathVariable Long restaurantId,
                                  @RequestBody @Valid RestaurantInput restaurantInput) {
        try {
//        Restaurant restaurant = restaurantInputDismantle.toDomainObject(restaurantInput);
            Restaurant currentRestaurant = registrationRestaurant.searchOrFail(restaurantId);

            restaurantInputDismantle.copyToDomainObject(restaurantInput, currentRestaurant);

//        BeanUtils.copyProperties(restaurant, currentRestaurant,
//                "id", "payment", "address", "recordDate");

            return restaurantModelAssembler.toModel(registrationRestaurant.add(currentRestaurant));
        } catch (KitchenNotFoundException | CityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * @param restaurantId
     * @return
     * @PutMapping("/{restaurantId}/active") || @DeleteMapping("/{restaurantId}/inactive")
     * They are one thing to do!
     */

    @CheckSecurity.Restaurants.AllowedToEdit
    @PutMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> active(@PathVariable Long restaurantId) {
        registrationRestaurant.active(restaurantId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurants.AllowedToEdit
    @DeleteMapping("/{restaurantId}/inactive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inactive(@PathVariable Long restaurantId) {
        registrationRestaurant.inactive(restaurantId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurants.AllowedToEdit
    @PutMapping("/activations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateMultiples(@RequestBody List<Long> restaurantIds) {
        try {
            registrationRestaurant.active(restaurantIds);
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Restaurants.AllowedToEdit
    @DeleteMapping("/activations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateMultiples(@RequestBody List<Long> restaurantIds) {
        try {
            registrationRestaurant.inactive(restaurantIds);
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Restaurants.AllowedToEdit
    @PutMapping("/{restaurantId}/opening")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> open(@PathVariable Long restaurantId) {
        registrationRestaurant.open(restaurantId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurants.AllowedToEdit
    @PutMapping("/{restaurantId}/closing")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> close(@PathVariable Long restaurantId) {
        registrationRestaurant.close(restaurantId);

        return ResponseEntity.noContent().build();
    }

//    @PatchMapping("/{restaurantId}")
//    public Restaurant partialUpdate(@PathVariable Long restaurantId,
//                                    @RequestBody Map<String, Object> fields, HttpServletRequest request) {
//
//        Restaurant currentRestaurant = registrationRestaurant.searchOrFail(restaurantId);
//
//        merge(fields, currentRestaurant, request);
//
//        validation(currentRestaurant, "restaurant");
//
//        return update(restaurantId, currentRestaurant);
//    }
//
//    private void validation(Restaurant restaurant, String objectName) {
//        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurant, objectName);
//
//        validator.validate(restaurant, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            throw new ExceptionValidation(bindingResult);
//        }
//    }
//
//    private void merge(Map<String, Object> sourceData, Restaurant destinyRestaurant,
//                       HttpServletRequest request) { //destinyRestaurant = currentRestaurant
//        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//
//            Restaurant sourceRestaurant = objectMapper.convertValue(sourceData, Restaurant.class);
//
//            sourceData.forEach((propertyName, propertyValue) -> {
//                Field field = ReflectionUtils.findField(Restaurant.class, propertyName);
//                field.setAccessible(true); //Here, we "break" the private method in restaurant by accessing it
//
//                Object newValue = ReflectionUtils.getField(field, sourceRestaurant);
//
//                System.out.println(propertyName + " = " + propertyValue);
//
//                ReflectionUtils.setField(field, destinyRestaurant, newValue);
//            });
//        } catch (IllegalArgumentException e) {
//            Throwable rootCause = ExceptionUtils.getRootCause(e);
//            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
//        }
//    }

    @CheckSecurity.Restaurants.AllowedToEdit
    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long restaurantId) {
        registrationRestaurant.remove(restaurantId);
    }
}
