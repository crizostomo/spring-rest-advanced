package com.developer.beverageapi.domain.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class KitchenIDInput {

    @NotNull
    private Long id;
}
