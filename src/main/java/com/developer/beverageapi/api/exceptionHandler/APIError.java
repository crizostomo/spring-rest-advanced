package com.developer.beverageapi.api.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class APIError {

    @ApiModelProperty(example = "400")
    private Integer status;

    @ApiModelProperty(example = "https://toogle.com.br/invalid-data")
    private String type;

    @ApiModelProperty(example = "Invalid data")
    private String title;

    @ApiModelProperty(example = "One or more fields are invalid")
    private String detail;

    private String userMessage;

    @ApiModelProperty(example = "2022-11-01T10:33:02.70844Z")
    private OffsetDateTime timestamp;

    @ApiModelProperty(example = "Objects or fields that generated the error (optional)")
    private List<Object> objects;

    @ApiModel("ProblemObj")
    @Getter
    @Builder
    public static class Object {

        @ApiModelProperty(example = "Price")
        private String name;

        @ApiModelProperty(example = "Price is mandatory")
        private String userMessage;
    }
}
