package com.developer.beverageapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Rice, Beans, Meat and Salad")
    private String name;

    @ApiModelProperty(example = "Extra: French Fries or Smashed Potato")
    private String description;

    @ApiModelProperty(example = "12.50")
    private BigDecimal price;

    @ApiModelProperty(example = "True")
    private Boolean active;
}
