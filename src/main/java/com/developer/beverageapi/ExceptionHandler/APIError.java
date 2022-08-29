package com.developer.beverageapi.ExceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class APIError {

    private Integer status;
    private String type;
    private String title;
    private String detail;
}
