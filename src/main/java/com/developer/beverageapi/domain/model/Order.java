package com.developer.beverageapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal subTotal;

    @Column(nullable = false)
    private BigDecimal delivery;

    @Column(nullable = false)
    private BigDecimal total;

    @Embedded
    private Address deliveryAddress;

    private OrderStatus status;

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime creationDate;

    @CreationTimestamp
    private OffsetDateTime confirmationDate;

    @CreationTimestamp
    private OffsetDateTime cancellationDate;

    @CreationTimestamp
    private OffsetDateTime deliveryDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Payment payment;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user_client_id", nullable = false)
    private User client;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>();
}
