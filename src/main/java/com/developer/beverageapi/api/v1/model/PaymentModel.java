package com.developer.beverageapi.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "payments")
@Getter
@Setter
public class PaymentModel extends RepresentationModel<PaymentModel> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Credit Card")
    private String description;
}
