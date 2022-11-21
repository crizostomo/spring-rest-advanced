package com.developer.beverageapi.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "City", description = "It represents a city")
@Getter
@Setter
public class CityModel {

    @ApiModelProperty(value = "City Id", example = "1")
    private Long id;

    @ApiModelProperty(example = "Sao Paulo")
    private String name;

    private StateModel state;
}
