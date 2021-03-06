package com.developer.beverageapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

//@JsonRootName("Kitchen")  The representation in the API | Postman
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
//@Table(name = "kitchen")
public class Kitchen {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonIgnore - It ignores this property
    @JsonProperty("name") //The representation in the API | Postman
    @Column (name = "name", length = 30, nullable = false) //Optional if you want to use the same name
    private String name;

}
