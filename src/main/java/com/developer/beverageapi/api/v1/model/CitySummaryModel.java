package com.developer.beverageapi.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cities")
@Getter
@Setter
public class CitySummaryModel extends RepresentationModel<CitySummaryModel> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Varzea Paulista")
    private String name;

    @ApiModelProperty(example = "Sao Paulo")
    private String stateName;
}
