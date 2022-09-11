package com.developer.beverageapi.core.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Getter
public class ExceptionValidation extends RuntimeException {

    private BindingResult bindingResult;
}
