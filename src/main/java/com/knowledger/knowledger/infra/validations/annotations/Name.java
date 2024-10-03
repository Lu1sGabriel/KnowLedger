package com.knowledger.knowledger.infra.validations.annotations;

import java.lang.annotation.*;

import com.knowledger.knowledger.infra.validations.validators.NameValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = NameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Name {
    String message() default "{Name}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
