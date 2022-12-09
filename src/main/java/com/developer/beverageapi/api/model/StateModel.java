package com.developer.beverageapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class StateModel extends RepresentationModel<StateModel> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Sao Paulo")
    private String name;
}
