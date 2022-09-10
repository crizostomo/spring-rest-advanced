package com.developer.beverageapi.domain.model;

import com.developer.beverageapi.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

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
    @NotBlank
    private String name;

    @Valid
    @NotNull
    @ConvertGroup(from = Default.class, to = Groups.StateId.class)
    @ManyToOne//(fetch = FetchType.LAZY) //Many states have one city
    @JoinColumn(name = "state_id") //In JPA this is generated automatically if you don't put the name
    private State state;
}
