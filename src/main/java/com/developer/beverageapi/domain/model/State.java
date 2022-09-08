package com.developer.beverageapi.domain.model;

import com.developer.beverageapi.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
//@Table(name = "State")
public class State {

    @EqualsAndHashCode.Include
    @NotNull(groups = Groups.StateId.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "name", nullable = false)
    @NotBlank
    private String name;

}
