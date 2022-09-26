package com.developer.beverageapi.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Embeddable //It is possible to be added to another class
public class Address {

    @Column(name = "address_cep")
    private String cep;

    @Column(name = "address_street")
    private String street;

    @Column(name = "address_number")
    private String number;

    @Column(name = "address_complement")
    private String complement;

    @Column(name = "address_neighborhood")
    private String neighborhood;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_city_id")
    private City city;
}
