package com.developer.beverageapi.domain.model.mixin;

import com.developer.beverageapi.domain.model.Restaurant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class KitchenMixin {
    //    @JsonIgnore - It ignores this property
    @JsonProperty("name") //The representation in the API | Postman
    private String name;

    @JsonIgnore
    private List<Restaurant> restaurants = new ArrayList<>(); //new ArrayList<>() to start an empty list to avoid nullPointer exception

}
