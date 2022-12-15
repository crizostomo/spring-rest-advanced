package com.developer.beverageapi.domain.model;

import com.developer.beverageapi.domain.event.OrderCancelledEvent;
import com.developer.beverageapi.domain.event.OrderConfirmedEvent;
import com.developer.beverageapi.domain.exception.BusinessException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Table(name = "`order`")
public class Order extends AbstractAggregateRoot<Order> {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

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

    public void confirm() {
        setStatus(OrderStatus.CONFIRMED);
        setConfirmationDate(OffsetDateTime.now());

        registerEvent(new OrderConfirmedEvent(this));
    }

    public void deliver() {
        setStatus(OrderStatus.DELIVERED);
        setDeliveryDate(OffsetDateTime.now());
    }

    public void cancel() {
        setStatus(OrderStatus.CANCELLED);
        setCancelledDate(OffsetDateTime.now());

        registerEvent(new OrderCancelledEvent(this));
    }

    public boolean canBeConfirmed() {
        return getStatus().canBeAlteredToANewStatus(OrderStatus.CONFIRMED);
    }

    public boolean canBeCancelled() {
        return getStatus().canBeAlteredToANewStatus(OrderStatus.CANCELLED);
    }

    public boolean canBeDelivered() {
        return getStatus().canBeAlteredToANewStatus(OrderStatus.DELIVERED);
    }

    private void setStatus(OrderStatus newStatus) {
        if (getStatus().cannotBeAlteredToANewStatus(newStatus)) {
            throw new BusinessException(
                    String.format("Order status %d cannot be altered from %s to %s",
                            getCode(), getStatus().getDescription(), newStatus.getDescription()));
        }
        this.status = newStatus;
    }

    @PrePersist // Before inserting a new register order in DB execute this method
    private void generateCode() {
        setCode(UUID.randomUUID().toString());
    }
}
