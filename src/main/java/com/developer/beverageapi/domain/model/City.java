package com.developer.beverageapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
//@Table(name = "city")
public class City {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "name", nullable = false) //Optional if you want to use the same name
    private String name;

    @ManyToOne//(fetch = FetchType.LAZY) //Many states have one city
    @JoinColumn(name = "state_id") //In JPA this is generated automatically if you don't put the name
    private State state;

}
