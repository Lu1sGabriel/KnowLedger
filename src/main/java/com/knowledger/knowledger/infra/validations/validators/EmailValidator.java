package com.knowledger.knowledger.infra.validations.validators;

import java.util.Set;

import com.knowledger.knowledger.infra.validations.annotations.Email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {

    private static final Set<String> ALLOWED_DOMAINS = Set.of(
            "gmail.com", "hotmail.com", "outlook.com", "protonmail.com", "proton.me",
            "yahoo.com", "icloud.com", "aol.com", "zohomail.eu", "gmx.com", "gmx.us");
    private static final String EMAIL_FORMAT_ERROR_MESSAGE = "Formato de email inválido.";
    private static final String EMAIL_NULL_ERROR_MESSAGE = "Email não pode ser nulo.";
    private static final String EMAIL_DOMAIN_ERROR_MESSAGE = "Domínio de email não permitido.";

    @Override
    public void initialize(Email email) {
    }

    @Override
    public boolean isValid(String emailField, ConstraintValidatorContext cxt) {
        if (emailField == null) {
            throw new IllegalArgumentException(EMAIL_NULL_ERROR_MESSAGE);
        }
        validateEmailFormat(emailField);
        validateEmailDomain(emailField);

        return true;
    }

    private void validateEmailFormat(String emailField) {
        int atIndex = emailField.indexOf('@');
        int dotIndex = emailField.lastIndexOf('.');

        if (atIndex == -1 || dotIndex == -1 || atIndex > dotIndex || dotIndex == emailField.length() - 1) {
            throw new IllegalArgumentException(EMAIL_FORMAT_ERROR_MESSAGE);
        }
    }

    private void validateEmailDomain(String emailField) {
        String domain = emailField.substring(emailField.indexOf('@') + 1);
        if (!ALLOWED_DOMAINS.contains(domain)) {
            throw new IllegalArgumentException(EMAIL_DOMAIN_ERROR_MESSAGE);
        }
    }

}