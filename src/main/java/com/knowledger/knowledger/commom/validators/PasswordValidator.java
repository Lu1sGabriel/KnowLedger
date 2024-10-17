package com.knowledger.knowledger.commom.validators;

import com.knowledger.knowledger.commom.annotations.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*(),.?\":{}|<>";

    private static final String PASSWORD_NULL_ERROR_MESSAGE = "A senha não pode ser nula.";
    private static final String PASSWORD_LENGTH_ERROR_MESSAGE = "A senha deve ter no mínimo " + MIN_PASSWORD_LENGTH
            + " caracteres.";
    private static final String PASSWORD_SPECIAL_CHAR_ERROR_MESSAGE = "A senha deve conter pelo menos um caractere especial.";
    private static final String PASSWORD_UPPERCASE_ERROR_MESSAGE = "A senha deve conter pelo menos uma letra maiúscula.";
    private static final String PASSWORD_DIGIT_ERROR_MESSAGE = "A senha deve conter pelo menos um número.";
    private static final String PASSWORD_REPEATED_CHAR_ERROR_MESSAGE = "A senha não pode conter mais de três caracteres repetidos consecutivos.";
    private static final String PASSWORD_INVALID_CHAR_ERROR_MESSAGE = "A senha não pode conter caracteres inválidos como 'ç'.";

    private static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern
            .compile("[" + Pattern.quote(SPECIAL_CHARACTERS) + "]");
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d");
    private static final Pattern INVALID_CHAR_PATTERN = Pattern.compile("[çÇ]");

    @Override
    public void initialize(Password constraintAnnotation) {
    }

    @Override
    public boolean isValid(String passwordField, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        if (passwordField == null) {
            addViolation(context, PASSWORD_NULL_ERROR_MESSAGE);
            return false;
        }

        if (passwordField.length() < MIN_PASSWORD_LENGTH) {
            addViolation(context, PASSWORD_LENGTH_ERROR_MESSAGE);
            return false;
        }

        if (!SPECIAL_CHARACTER_PATTERN.matcher(passwordField).find()) {
            addViolation(context, PASSWORD_SPECIAL_CHAR_ERROR_MESSAGE);
            return false;
        }

        if (!UPPERCASE_PATTERN.matcher(passwordField).find()) {
            addViolation(context, PASSWORD_UPPERCASE_ERROR_MESSAGE);
            return false;
        }

        if (!DIGIT_PATTERN.matcher(passwordField).find()) {
            addViolation(context, PASSWORD_DIGIT_ERROR_MESSAGE);
            return false;
        }

        if (containsInvalidCharacters(passwordField)) {
            addViolation(context, PASSWORD_INVALID_CHAR_ERROR_MESSAGE);
            return false;
        }

        if (containsMoreThanThreeRepeatedCharacters(passwordField)) {
            addViolation(context, PASSWORD_REPEATED_CHAR_ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void addViolation(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

    private boolean containsInvalidCharacters(String password) {
        return INVALID_CHAR_PATTERN.matcher(password).find();
    }

    private boolean containsMoreThanThreeRepeatedCharacters(String password) {
        String normalizedPassword = password.toLowerCase();
        int repeatCount = 1;

        for (int i = 1; i < normalizedPassword.length(); i++) {
            if (normalizedPassword.charAt(i) == normalizedPassword.charAt(i - 1)) {
                repeatCount++;
                if (repeatCount > 3) {
                    return true;
                }
            } else {
                repeatCount = 1;
            }
        }
        return false;
    }

}