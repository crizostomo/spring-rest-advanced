package com.developer.beverageapi.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantInput {

    @ApiModelProperty(example = "O Matuto", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(example = "12.00", required = true)
    @NotNull
    @PositiveOrZero
    private BigDecimal delivery;

    @Valid
    @NotNull
    private KitchenIDInput kitchen;

    @Valid
    @NotNull
    private AddressInput address;
}
