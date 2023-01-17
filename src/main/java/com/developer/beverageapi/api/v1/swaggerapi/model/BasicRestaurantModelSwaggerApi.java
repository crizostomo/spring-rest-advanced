package com.developer.beverageapi.api.v1.swaggerapi.model;

import com.developer.beverageapi.api.v1.model.KitchenModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@ApiModel("BasicRestaurantModel")
@Getter
@Setter
public class BasicRestaurantModelSwaggerApi {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "O Matuto")
    private String name;

    @ApiModelProperty(example = "12.00")
    private BigDecimal delivery;

    private KitchenModel kitchen;
}
