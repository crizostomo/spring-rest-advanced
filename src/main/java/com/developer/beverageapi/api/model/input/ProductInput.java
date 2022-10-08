package com.developer.beverageapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductInput {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    @PositiveOrZero
    private BigDecimal price;

    @NotBlank
    private Boolean active;
}
