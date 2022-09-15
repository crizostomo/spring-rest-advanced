package com.developer.beverageapi.domain.model;

import com.developer.beverageapi.core.validation.Groups;
import com.developer.beverageapi.core.validation.Multiple;
import com.developer.beverageapi.core.validation.ZeroValueIncludesDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ZeroValueIncludesDescription(fieldValue = "delivery",
        fieldDescription = "name", mandatoryField = "Free Delivery")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
//@Table(name = "restaurant")
public class Restaurant {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column (name = "name", nullable = false) //Optional if you want to use the same name
    private String name;

//    @DecimalMin("0")
//    @Delivery
    @Multiple(number = 5)
    @PositiveOrZero
    @NotNull //PositiveOrZero does not validate if it is null
    @Column (name = "delivery", nullable = false)
    private BigDecimal delivery;

//    @JsonIgnore
//    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "name"}, allowGetters = true)
    @Valid
    @NotNull
    @ConvertGroup(from = Default.class, to = Groups.KitchenId.class)
    @ManyToOne//(fetch = FetchType.LAZY) //Many restaurants own one kitchen | Everything that finishes with ...ToOne uses the strategy 'Eager Loading'
    @JoinColumn(name = "kitchen_id") //In JPA this is generated automatically if you don't put the name
    private Kitchen kitchen;

//    @JsonIgnore
    @Embedded //This property is part of the restaurant entity
    private Address address;

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
    private List<Payment> payments = new ArrayList<>();

//    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();
}
