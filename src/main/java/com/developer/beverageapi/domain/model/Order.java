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

    private BigDecimal subtotal;
    private BigDecimal delivery;
    private BigDecimal total;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // This annotation converts a String to an Enum
    private OrderStatus status = OrderStatus.CREATED;

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime creationDate;

    private OffsetDateTime confirmationDate;
    private OffsetDateTime cancelledDate;
    private OffsetDateTime deliveryDate;

    @ManyToOne(fetch = FetchType.LAZY) // To reduce the quantity in consults - OrderSummaryModel does not have| 12.20
    @JoinColumn(nullable = false)
    private Payment payment;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user_client_id", nullable = false)
    private User client;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    public void calculateTotal() {
        getItems().forEach(OrderItem::calculateTotalPrice);

        this.subtotal = getItems()
                .stream()
                .map(orderItem -> orderItem.getTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.total = this.subtotal.add(this.delivery);
    }
}
