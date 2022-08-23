package com.developer.beverageapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private LocalDateTime creationDate;

    @CreationTimestamp
    private LocalDateTime confirmationDate;

    @CreationTimestamp
    private LocalDateTime cancellationDate;

    @CreationTimestamp
    private LocalDateTime deliveryDate;

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
