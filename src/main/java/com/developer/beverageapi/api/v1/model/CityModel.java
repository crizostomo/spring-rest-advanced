package com.developer.beverageapi.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

//@ApiModel(value = "City", description = "It represents a city")

@Relation(collectionRelation = "cities")
@Getter
@Setter
public class CityModel extends RepresentationModel<CityModel> {

    @ApiModelProperty(value = "City Id", example = "1")
    private Long id;

    @ApiModelProperty(example = "Sao Paulo")
    private String name;

    private StateModel state;
}
