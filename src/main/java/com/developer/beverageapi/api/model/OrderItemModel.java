package com.developer.beverageapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemModel extends RepresentationModel<OrderItemModel> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Grilled beef")
    private String name;

    @ApiModelProperty(example = "2")
    private Integer quantity;

    @ApiModelProperty(example = "74.40")
    private BigDecimal unitPrice;

    @ApiModelProperty(example = "148.80")
    private BigDecimal total;

    @ApiModelProperty(example = "Less Spicy")
    private String observation;
}
