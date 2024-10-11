package com.knowledger.knowledger.commom.validators;

import java.util.regex.Pattern;

import com.knowledger.knowledger.commom.annotations.Email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {

    private static final String EMAIL_NULL_ERROR_MESSAGE = "Email não pode ser nulo.";
    private static final String EMAIL_FORMAT_ERROR_MESSAGE = "Formato de email inválido.";

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-.]+@[\\w-]+\\.[\\w-.]+$");

    @Override
    public void initialize(Email constraintAnnotation) {
    }

    @Override
    public boolean isValid(String emailField, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        if (emailField == null) {
            addViolation(context, EMAIL_NULL_ERROR_MESSAGE);
            return false;
        }

        if (!isValidEmailFormat(emailField)) {
            addViolation(context, EMAIL_FORMAT_ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void addViolation(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

    private boolean isValidEmailFormat(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

}