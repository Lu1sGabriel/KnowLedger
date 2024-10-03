package com.knowledger.knowledger.infra.validations.annotations;

import java.lang.annotation.*;

import com.knowledger.knowledger.infra.validations.validators.EmailValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {
    String message() default "{Email inválido}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}