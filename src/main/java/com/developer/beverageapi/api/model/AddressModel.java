package com.developer.beverageapi.api.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressModel {

    @ApiModelProperty(example = "38444-000")
    private String cep;

    @ApiModelProperty(example = "California Street")
    private String street;

    @ApiModelProperty(example = "123")
    private String number;

    @ApiModelProperty(example = "House 2")
    private String complement;

    @ApiModelProperty(example = "Downtown")
    private String neighborhood;

    private CitySummaryModel city;
}
