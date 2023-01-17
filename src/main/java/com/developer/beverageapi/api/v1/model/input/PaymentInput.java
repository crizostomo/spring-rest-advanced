package com.developer.beverageapi.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PaymentInput {

    @ApiModelProperty(example = "Credit Card", required = true)
    @NotBlank
    private String description;
}
