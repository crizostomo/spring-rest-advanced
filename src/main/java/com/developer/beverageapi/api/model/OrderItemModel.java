package com.developer.beverageapi.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemModel {

    private Long id;
    private String name;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal total;
    private String observation;
}
