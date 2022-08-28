package com.developer.beverageapi.ExceptionHandler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class APIError {

    private LocalDateTime dateTime;
    private String message;
}
