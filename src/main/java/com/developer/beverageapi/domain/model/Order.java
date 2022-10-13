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
@Table(name = "`order`")
public class Order {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private BigDecimal delivery;

    @Column(nullable = false)
    private BigDecimal total;

    @Embedded
    private Address deliveryAddress;

    @Enumerated(EnumType.STRING) // This annotation converts a String to an Enum
    private OrderStatus status;

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime creationDate;

    @CreationTimestamp
    private OffsetDateTime confirmationDate;

    @CreationTimestamp
    private OffsetDateTime cancelledDate;

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

    public void calculateTotal() {
        this.subtotal = getItems()
                .stream()
                .map(orderItem -> orderItem.getTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.total = this.subtotal.add(this.delivery);
    }

    public void defineDelivery() {
        setDelivery(getRestaurant().getDelivery());
    }

    public void attribute() {
        getItems().forEach(orderItem -> orderItem.setOrder(this));
    }
}
