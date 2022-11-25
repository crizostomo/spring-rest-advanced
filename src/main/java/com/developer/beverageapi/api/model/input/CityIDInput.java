package com.developer.beverageapi.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityIDInput {

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long id;
}
