package com.developer.beverageapi.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StateInput {

    @Schema(example = "Sao Paulo", required = true)
    @NotBlank
    private String name;
}
