package com.developer.beverageapi.api.v2.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@ApiModel("KitchenInput")
@Relation(collectionRelation = "kitchens")
@Getter
@Setter
public class KitchenModelV2 extends RepresentationModel<KitchenModelV2> {

    @ApiModelProperty(example = "1")
    private Long kitchenId;

    @ApiModelProperty(example = "Brazilian")
    private String kitchenName;
}
