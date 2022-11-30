package com.developer.beverageapi.api.model;

import com.developer.beverageapi.api.model.input.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantModel {

    @ApiModelProperty(example = "1")
    @JsonView(RestaurantView.Summary.class)
    private Long id;

    @ApiModelProperty(example = "O Matuto")
    @JsonView(RestaurantView.Summary.class)
    private String name;

    @ApiModelProperty(example = "12.00")
    @JsonView(RestaurantView.Summary.class)
    private BigDecimal delivery;

    @JsonView(RestaurantView.Summary.class)
    private KitchenModel kitchen;

    private Boolean active;
    private Boolean open;
    private AddressModel address;
}
