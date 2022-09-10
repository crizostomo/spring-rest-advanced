package com.developer.beverageapi.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = { ZeroValueIncludesDescriptionValidator.class })
public @interface ZeroValueIncludesDescription {

    String message() default "Mandatory description invalid";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String fieldValue();

    String fieldDescription();

    String mandatoryField();


}
