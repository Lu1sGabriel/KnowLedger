package com.knowledger.knowledger.infra.validations.validators;

import java.util.Set;
import java.util.regex.Pattern;

import com.knowledger.knowledger.infra.validations.annotations.Email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {

    private static final Set<String> ALLOWED_DOMAINS = Set.of(
            "gmail.com", "hotmail.com", "outlook.com", "protonmail.com", "proton.me",
            "yahoo.com", "icloud.com", "aol.com", "zohomail.eu", "gmx.com", "gmx.us");

    private static final String EMAIL_NULL_ERROR_MESSAGE = "Email não pode ser nulo.";
    private static final String EMAIL_FORMAT_ERROR_MESSAGE = "Formato de email inválido.";
    private static final String EMAIL_DOMAIN_ERROR_MESSAGE = "Domínio de email não permitido.";

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-.]+@[\\w-]+\\.[\\w-.]+$");

    @Override
    public void initialize(Email constraintAnnotation) {
    }

    @Override
    public boolean isValid(String emailField, ConstraintValidatorContext context) {
        boolean isValid = true;

        context.disableDefaultConstraintViolation();

        if (emailField == null) {
            addViolation(context, EMAIL_NULL_ERROR_MESSAGE);
            return false;
        }

        if (!isValidEmailFormat(emailField)) {
            addViolation(context, EMAIL_FORMAT_ERROR_MESSAGE);
            isValid = false;
        }

        if (!isAllowedDomain(emailField)) {
            addViolation(context, EMAIL_DOMAIN_ERROR_MESSAGE);
            isValid = false;
        }

        return isValid;
    }

    private void addViolation(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

    private boolean isValidEmailFormat(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean isAllowedDomain(String email) {
        String domain = email.substring(email.indexOf('@') + 1).toLowerCase();
        return ALLOWED_DOMAINS.contains(domain);
    }

}