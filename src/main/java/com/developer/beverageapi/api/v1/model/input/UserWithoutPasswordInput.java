package com.developer.beverageapi.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserWithoutPasswordInput {

    @ApiModelProperty(example = "Roronoa Zoro", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(example = "roronoa.zoro@onepiece.com", required = true)
    @Email
    @NotBlank
    private String email;
}
