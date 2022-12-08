package com.developer.beverageapi.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserWithPasswordInput {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @ApiModelProperty(example = "123", required = true)
    @NotBlank
    private String password;
}
