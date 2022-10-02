package com.developer.beverageapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserPasswordInput {

    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;
}
