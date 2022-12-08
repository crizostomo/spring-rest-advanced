package com.developer.beverageapi.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserPasswordInput {

    @ApiModelProperty(example = "123", required = true)
    @NotBlank
    private String currentPassword;

    @ApiModelProperty(example = "123", required = true)
    @NotBlank
    private String newPassword;
}
