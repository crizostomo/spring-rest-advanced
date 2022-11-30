package com.developer.beverageapi.api.model.input;

import com.developer.beverageapi.api.model.CitySummaryModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressInput {

    @ApiModelProperty(example = "38444-000", required = true)
    @NotBlank
    private String cep;

    @ApiModelProperty(example = "California Street", required = true)
    @NotBlank
    private String street;

    @ApiModelProperty(example = "\"1200\"", required = true)
    @NotBlank
    private String number;

    @ApiModelProperty(example = "House 2", required = true)
    private String complement;

    @ApiModelProperty(example = "Downtown", required = true)
    @NotBlank
    private String neighborhood;

    @Valid
    @NotNull
    private CityIDInput city;
}
