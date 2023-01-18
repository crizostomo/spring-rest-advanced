package com.developer.beverageapi.api.v2.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

//@ApiModel(value = "City", description = "It represents a city")

@ApiModel("CityModel")
@Relation(collectionRelation = "cities")
@Getter
@Setter
public class CityModelV2 extends RepresentationModel<CityModelV2> {

    @ApiModelProperty(value = "City Id", example = "1")
    private Long cityId;

    @ApiModelProperty(example = "Sao Paulo")
    private String cityName;

    private Long stateId;

    private String stateName;
}
