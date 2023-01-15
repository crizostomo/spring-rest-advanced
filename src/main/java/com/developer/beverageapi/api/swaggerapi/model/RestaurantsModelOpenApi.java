package com.developer.beverageapi.api.swaggerapi.model;

import com.developer.beverageapi.api.model.RestaurantBasicModel;
import com.developer.beverageapi.api.model.RestaurantModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("RestaurantsModel")
@Setter
@Getter
public class RestaurantsModelOpenApi {

    private RestaurantsEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("RestaurantsEmbeddedModel")
    @Data
    public class RestaurantsEmbeddedModelOpenApi {

        private List<RestaurantModel> restaurants;
    }
}
