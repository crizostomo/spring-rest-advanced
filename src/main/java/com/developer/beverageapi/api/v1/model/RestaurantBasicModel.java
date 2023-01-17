package com.developer.beverageapi.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "restaurants")
@Getter
@Setter
public class RestaurantBasicModel extends RepresentationModel<RestaurantBasicModel> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "O Matuto")
    private String name;

    @ApiModelProperty(example = "12.00")
    private BigDecimal delivery;

    private KitchenModel kitchen;
}
