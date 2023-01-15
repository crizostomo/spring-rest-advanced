package com.developer.beverageapi.api.swaggerapi.model;

import com.developer.beverageapi.api.model.CityModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CitiesModel")
@Data
public class CitiesModelOpenApi {

    private CityEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("CitiesEmbeddedModel")
    @Data
    public class CityEmbeddedModelOpenApi {

        private List<CityModel> cities;
    }
}
