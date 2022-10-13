package com.developer.beverageapi.api.model;

import com.developer.beverageapi.domain.model.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderModel {

    private Long id;
    private BigDecimal subtotal;
    private BigDecimal delivery;
    private BigDecimal total;
    private AddressModel deliveryAddress;
    private String status;
    private OffsetDateTime creationDate;
    private OffsetDateTime confirmationDate;
    private OffsetDateTime cancelledDate;
    private OffsetDateTime deliveryDate;
    private PaymentModel payment;
    private RestaurantSummaryModel restaurant;
    private UserModel client;
    private List<OrderItemModel> items;
}
