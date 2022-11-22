package com.developer.beverageapi.api.model;

import com.developer.beverageapi.api.model.input.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KitchenModel {

    @ApiModelProperty(example = "1")
    @JsonView(RestaurantView.Summary.class)
    private Long id;

    @ApiModelProperty(example = "Brazilian")
    @JsonView(RestaurantView.Summary.class)
    private String name;
}
