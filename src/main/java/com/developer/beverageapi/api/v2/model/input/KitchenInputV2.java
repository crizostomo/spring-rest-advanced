package com.developer.beverageapi.api.v2.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class KitchenInputV2 {

    @ApiModelProperty(example = "Brazilian", required = true)
    @NotBlank
    private String kitchenName;
}
