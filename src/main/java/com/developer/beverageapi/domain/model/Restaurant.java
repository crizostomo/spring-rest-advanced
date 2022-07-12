package com.developer.beverageapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
//@Table(name = "restaurant")
public class Restaurant {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "name") //Optional if you want to use the same name
    private String name;

    @Column (name = "delivery")
    private BigDecimal delivery;

    @ManyToOne //Many restaurants own one kitchen
    @JoinColumn(name = "kitchen_id") //In JPA this is generated automatically if you don't put the name
    private Kitchen kitchen;

}
