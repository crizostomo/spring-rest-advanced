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
public class RestaurantModel extends RepresentationModel<RestaurantModel> {

    @ApiModelProperty(example = "1")
//    @JsonView(RestaurantView.Summary.class)
    private Long id;

    @ApiModelProperty(example = "O Matuto")
//    @JsonView(RestaurantView.Summary.class)
    private String name;

    @ApiModelProperty(example = "12.00")
//    @JsonView(RestaurantView.Summary.class)
    private BigDecimal delivery;

//    @JsonView(RestaurantView.Summary.class)
    private KitchenModel kitchen;

    private Boolean active;
    private Boolean open;
    private AddressModel address;
}
