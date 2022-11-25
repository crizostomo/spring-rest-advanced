package com.developer.beverageapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantSummaryModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "O Matuto")
    private String name;
}
