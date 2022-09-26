package com.developer.beverageapi.api.model;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressModel {

    private String cep;

    private String street;

    private String number;

    private String complement;

    private String neighborhood;

    private CitySummaryModel city;
}
