package com.developer.beverageapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "CONSULT_KITCHENS")
    private String name;

    @ApiModelProperty(example = "It allows consulting kitchens")
    private String description;
}
