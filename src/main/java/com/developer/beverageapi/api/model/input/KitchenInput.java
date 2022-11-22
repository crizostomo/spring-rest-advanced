package com.developer.beverageapi.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class KitchenInput {

    @ApiModelProperty(example = "Brazilian", required = true)
    @NotBlank
    private String name;
}
