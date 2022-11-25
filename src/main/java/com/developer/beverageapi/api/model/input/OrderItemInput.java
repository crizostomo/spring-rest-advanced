package com.developer.beverageapi.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class OrderItemInput {

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long productId;

    @ApiModelProperty(example = "2", required = true)
    @NotNull
    @PositiveOrZero
    private Integer quantity;

    @ApiModelProperty(example = "Less spicy", required = true)
    private String observation;
}
