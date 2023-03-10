package com.developer.beverageapi.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StateIDInput {

    @Schema(example = "1", required = true)
    @NotNull
    private Long id;
}
