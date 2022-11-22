package com.developer.beverageapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Gold")
    private String name;
}
