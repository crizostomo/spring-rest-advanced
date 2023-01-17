package com.developer.beverageapi.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductInput {

    @ApiModelProperty(example = "Rice, Beans, Meat and Salad", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(example = "Extra: French Fries or Smashed Potato", required = true)
    @NotBlank
    private String description;

    @ApiModelProperty(example = "12.50", required = true)
    @NotBlank
    @PositiveOrZero
    private BigDecimal price;

    @ApiModelProperty(example = "True", required = true)
    @NotBlank
    private Boolean active;
}
