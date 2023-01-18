package com.developer.beverageapi.api.v2.swaggerapi.model;

import com.developer.beverageapi.api.v2.model.CityModelV2;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CitiesModel")
@Data
public class CitiesModelV2OpenApi {

    private CityEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("CitiesEmbeddedModel")
    @Data
    public class CityEmbeddedModelOpenApi {

        private List<CityModelV2> cities;
    }
}
