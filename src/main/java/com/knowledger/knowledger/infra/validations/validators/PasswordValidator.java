package com.knowledger.knowledger.infra.validations.validators;

import com.knowledger.knowledger.infra.validations.annotations.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*(),.?\":{}|<>";
    private static final String LENGTH_ERROR_MESSAGE = "A senha deve ter no mínimo 8 caracteres.";
    private static final String SPECIAL_CHAR_ERROR_MESSAGE = "A senha deve conter pelo menos um caractere especial.";
    private static final String UPPERCASE_ERROR_MESSAGE = "A senha deve conter pelo menos uma letra maiúscula.";
    private static final String NUMBER_ERROR_MESSAGE = "A senha deve conter pelo menos um número.";
    private static final String SEQUENCE_ERROR_MESSAGE = "A senha não pode conter números sequenciais ou caracteres repetidos.";

    @Override
    public void initialize(Password password) {
    }

    @Override
    public boolean isValid(String passwordField, ConstraintValidatorContext context) {
        if (passwordField == null) {
            throw new IllegalArgumentException("A senha não pode ser nula.");
        }
        validatePasswordLength(passwordField);
        validateSpecialCharacter(passwordField);
        validateUppercaseCharacter(passwordField);
        validateNumber(passwordField);
        validateNoSequentialNumbers(passwordField);
        return true;
    }

    private void validatePasswordLength(String passwordField) {
        if (passwordField.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException(LENGTH_ERROR_MESSAGE);
        }
    }

    private void validateSpecialCharacter(String passwordField) {
        for (char c : passwordField.toCharArray()) {
            if (SPECIAL_CHARACTERS.indexOf(c) != -1) {
                return;
            }
        }
        throw new IllegalArgumentException(SPECIAL_CHAR_ERROR_MESSAGE);
    }

    private void validateUppercaseCharacter(String passwordField) {
        if (passwordField.chars().noneMatch(Character::isUpperCase)) {
            throw new IllegalArgumentException(UPPERCASE_ERROR_MESSAGE);
        }
    }

    private void validateNumber(String passwordField) {
        if (passwordField.chars().noneMatch(Character::isDigit)) {
            throw new IllegalArgumentException(NUMBER_ERROR_MESSAGE);
        }
    }

    private void validateNoSequentialNumbers(String passwordField) {
        char[] chars = passwordField.toCharArray();
        for (int i = 0; i < chars.length - 2; i++) {
            if (chars[i] == chars[i + 1] && chars[i] == chars[i + 2]) {
                throw new IllegalArgumentException(SEQUENCE_ERROR_MESSAGE);
            }
            if (Character.isDigit(chars[i]) && Character.isDigit(chars[i + 1]) && Character.isDigit(chars[i + 2])) {
                int first = Character.getNumericValue(chars[i]);
                int second = Character.getNumericValue(chars[i + 1]);
                int third = Character.getNumericValue(chars[i + 2]);

                if ((second == first + 1 && third == second + 1) || (second == first - 1 && third == second - 1)) {
                    throw new IllegalArgumentException(SEQUENCE_ERROR_MESSAGE);
                }
            }
        }
    }

}