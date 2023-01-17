package com.developer.beverageapi.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Relation(collectionRelation = "orders")
@Getter
@Setter
public class OrderModel extends RepresentationModel<OrderModel> {

    @ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
    private String code;

    @ApiModelProperty(example = "198.90")
    private BigDecimal subtotal;

    @ApiModelProperty(example = "10")
    private BigDecimal delivery;

    @ApiModelProperty(example = "208.90")
    private BigDecimal total;

    private AddressModel deliveryAddress;

    @ApiModelProperty(example = "CREATED")
    private String status;

    @ApiModelProperty(example = "2022-11-01T20:34:04Z")
    private OffsetDateTime creationDate;

    @ApiModelProperty(example = "2022-11-01T20:35:08Z")
    private OffsetDateTime confirmationDate;

    @ApiModelProperty(example = "2022-11-01T21:44:04Z")
    private OffsetDateTime cancelledDate;

    @ApiModelProperty(example = "2022-11-01T20:54:04Z")
    private OffsetDateTime deliveryDate;
    private PaymentModel payment;
    private RestaurantSummaryModel restaurant;
    private UserModel client;
    private List<OrderItemModel> items;
}
