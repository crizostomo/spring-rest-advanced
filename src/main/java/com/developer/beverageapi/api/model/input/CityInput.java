package com.developer.beverageapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class CityInput {

    @NotBlank
    private String name;

    @Valid
    @NotNull
    private StateIDInput state;
}
