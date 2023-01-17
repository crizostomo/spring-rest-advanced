package com.developer.beverageapi.api.v2.model.input;

import com.developer.beverageapi.api.v1.model.input.StateIDInput;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityInputV2 {

    @ApiModelProperty(example = "Sao Paulo", required = true)
    @NotBlank
    private String cityName;

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long stateId;
}
