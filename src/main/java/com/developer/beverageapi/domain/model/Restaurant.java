package com.developer.beverageapi.domain.model;

import com.developer.beverageapi.core.validation.ZeroValueIncludesDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@ZeroValueIncludesDescription(fieldValue = "delivery",
//        fieldDescription = "name", mandatoryField = "Free Delivery")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
//@Table(name = "restaurant")
public class Restaurant {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false) //Optional if you want to use the same name
    private String name;

    //    @DecimalMin("0")
//    @Delivery
//    @Multiple(number = 5)
    @PositiveOrZero
    @Column(name = "delivery", nullable = false)
    private BigDecimal delivery;

    //    @JsonIgnore
//    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "name"}, allowGetters = true)
//    @ConvertGroup(from = Default.class, to = Groups.KitchenId.class)
    @ManyToOne
//(fetch = FetchType.LAZY) //Many restaurants own one kitchen | Everything that finishes with ...ToOne uses the strategy 'Eager Loading'
    @JoinColumn(name = "kitchen_id", nullable = false) //In JPA this is generated automatically if you don't put the name
    private Kitchen kitchen;

    //    @JsonIgnore
    @Embedded //This property is part of the restaurant entity
    private Address address;

    private Boolean active = Boolean.TRUE;

    private Boolean open = Boolean.FALSE;

    //    @JsonIgnore
    @CreationTimestamp //The property must be salved with the current time
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime recordDate;

    //    @JsonIgnore
    @UpdateTimestamp //The property must be salved with the current time when updated
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime updatedDate;

    //    @JsonIgnore
    @ManyToMany//(fetch = FetchType.EAGER) // Everything that finishes with ...ToMany uses the strategy 'Lazy Loading'
    @JoinTable(name = "restaurant_payment",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_id"))
    private Set<Payment> payments = new HashSet<>(); // It changed from List to Set because it does not accept duplicated items

    @ManyToMany
    @JoinTable(name = "restaurant_user_responsible",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> responsible = new HashSet<>();

    //    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();

    public void active() {
        setActive(true);
    }

    public void inactive() {
        setActive(false);
    }

    public void open() {
        setOpen(true);
    }

    public void close() {
        setOpen(false);
    }

    public boolean removePayment(Payment payment) {
        return getPayments().remove(payment);
    }

    public boolean addPayment(Payment payment) {
        return getPayments().add(payment);
    }

    public boolean removeResponsible(User user) {
        return getResponsible().remove(user);
    }

    public boolean addResponsible(User user) {
        return getResponsible().add(user);
    }

    public boolean acceptPayment(Payment payment) {
        return getPayments().contains(payment);
    }

    public boolean doesNotAcceptPayment(Payment payment) {
        return !acceptPayment(payment);
    }

    public boolean isOpen() {
        return this.open;
    }

    public boolean isClosed() {
        return !this.open;
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean isInactive() {
        return !this.active;
    }

    public boolean openingAllowed() {
        return isActive() && isClosed();
    }

    public boolean closingAllowed() {
        return isOpen();
    }

    public boolean activationAllowed() {
        return isInactive();
    }

    public boolean inactivationAllowed() {
        return isActive();
    }
}
