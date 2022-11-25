package com.developer.beverageapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CitySummaryModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Varzea Paulista")
    private String name;

    @ApiModelProperty(example = "Sao Paulo")
    private String stateName;
}
