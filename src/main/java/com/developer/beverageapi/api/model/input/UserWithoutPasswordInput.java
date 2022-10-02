package com.developer.beverageapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserWithoutPasswordInput {

    @NotBlank
    private String name;

    @NotBlank
    private String email;
}
