package com.developer.beverageapi.controller;

import com.developer.beverageapi.assembler.RestaurantInputDismantle;
import com.developer.beverageapi.assembler.RestaurantModelAssembler;
import com.developer.beverageapi.domain.exception.BusinessException;
import com.developer.beverageapi.domain.exception.EntityNotFoundException;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.model.RestaurantModel;
import com.developer.beverageapi.domain.model.input.RestaurantInput;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import com.developer.beverageapi.domain.repository.RepositoryRestaurant;
import com.developer.beverageapi.domain.service.RestaurantRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class ControllerRestaurant {

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

    @GetMapping
    public List<RestaurantModel> list() {
        return restaurantModelAssembler.toCollectionModel(repositoryRestaurant.findAll());
    }

    @GetMapping("/{restaurantId}")
    public RestaurantModel search(@PathVariable Long restaurantId) {
        Restaurant restaurant = registrationRestaurant.searchOrFail(restaurantId);

        RestaurantModel restaurantModel = restaurantModelAssembler.toModel(restaurant);

        return restaurantModelAssembler.toModel(restaurant);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantModel add(@RequestBody @Valid RestaurantInput restaurantInput) {
        try {
            Restaurant restaurant = restaurantInputDismantle.toDomainObject(restaurantInput);

            return restaurantModelAssembler.toModel(registrationRestaurant.add(restaurant));
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{restaurantId}")
    public RestaurantModel update(@PathVariable Long restaurantId,
                             @RequestBody @Valid RestaurantInput restaurantInput) {
        Restaurant restaurant = restaurantInputDismantle.toDomainObject(restaurantInput);
        Restaurant currentRestaurant = registrationRestaurant.searchOrFail(restaurantId);

        BeanUtils.copyProperties(restaurant, currentRestaurant,
                "id", "payment", "address", "recordDate");

        try {
            return restaurantModelAssembler.toModel(registrationRestaurant.add(currentRestaurant));
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
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

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long restaurantId) {
        registrationRestaurant.remove(restaurantId);
    }
}
