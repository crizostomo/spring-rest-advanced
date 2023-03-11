package com.developer.beverageapi.api.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@Schema(name = "APIError")
public class APIError {

    @Schema(example = "400")
    private Integer status;

    @Schema(example = "https://toogle.com.br/invalid-data")
    private String type;

    @Schema(example = "Invalid data")
    private String title;

    @Schema(example = "One or more fields are invalid")
    private String detail;

    private String userMessage;

    @Schema(example = "2022-11-01T10:33:02.70844Z")
    private OffsetDateTime timestamp;

    @Schema(example = "Objects or fields that generated the error (optional)")
    private List<Object> objects;

    @Schema(name = "ProblemObj")
    @Getter
    @Builder
    public static class Object {

        @Schema(example = "Price")
        private String name;

        @Schema(example = "Price is mandatory")
        private String userMessage;
    }
}
