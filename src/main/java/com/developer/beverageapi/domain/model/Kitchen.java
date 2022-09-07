package com.developer.beverageapi.domain.model;

import com.developer.beverageapi.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

//@JsonRootName("Kitchen")  The representation in the API | Postman
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
//@Table(name = "kitchen")
public class Kitchen {

    @EqualsAndHashCode.Include
    @NotNull(groups = Groups.KitchenId.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonIgnore - It ignores this property
    @JsonProperty("name") //The representation in the API | Postman
    @NotBlank
    @Column (name = "name", length = 30, nullable = false) //Optional if you want to use the same name
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "kitchen")
    private List<Restaurant> restaurants = new ArrayList<>(); //new ArrayList<>() to start an empty list to avoid nullPointer exception
}
