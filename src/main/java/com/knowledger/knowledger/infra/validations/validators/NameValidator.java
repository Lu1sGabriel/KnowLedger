package com.knowledger.knowledger.infra.validations.validators;

import com.knowledger.knowledger.infra.validations.annotations.Name;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<Name, String> {

    private static final int MIN_LENGTH = 2;
    private static final String SPECIAL_CHARACTERS = "!@#$%&*()_+=-[]{};:/<>?|\"\\,.";

    private static final String NAME_NULL_ERROR_MESSAGE = "O nome não pode ser nulo.";
    private static final String INVALID_NAME_LENGTH_MESSAGE = "O nome deve ter no mínimo " + MIN_LENGTH
            + " caracteres.";
    private static final String NAME_WITH_INVALID_CHARACTERS_MESSAGE = "O nome deve conter apenas letras e espaços.";
    private static final String NAME_WITH_SPECIAL_CHARACTERS_MESSAGE = "O nome não pode conter caracteres especiais.";
    private static final String NAME_WITHOUT_SPACES_MESSAGE = "Nomes compostos devem conter espaços entre as palavras.";

    @Override
    public void initialize(Name constraintAnnotation) {
    }

    @Override
    public boolean isValid(String nameField, ConstraintValidatorContext context) {
        boolean isValid = true;

        context.disableDefaultConstraintViolation();

        if (nameField == null) {
            addViolation(context, NAME_NULL_ERROR_MESSAGE);
            return false;
        }

        if (nameField.length() < MIN_LENGTH) {
            addViolation(context, INVALID_NAME_LENGTH_MESSAGE);
            isValid = false;
        }

        if (!containsOnlyLettersAndSpaces(nameField)) {
            addViolation(context, NAME_WITH_INVALID_CHARACTERS_MESSAGE);
            isValid = false;
        }

        if (containsSpecialCharacters(nameField)) {
            addViolation(context, NAME_WITH_SPECIAL_CHARACTERS_MESSAGE);
            isValid = false;
        }

        if (containsInnerUppercaseLetters(nameField)) {
            addViolation(context, NAME_WITHOUT_SPACES_MESSAGE);
            isValid = false;
        }

        return isValid;
    }

    private void addViolation(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

    private boolean containsOnlyLettersAndSpaces(String name) {
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean containsSpecialCharacters(String name) {
        for (char c : name.toCharArray()) {
            if (SPECIAL_CHARACTERS.indexOf(c) != -1) {
                return true;
            }
        }
        return false;
    }

    private boolean containsInnerUppercaseLetters(String name) {
        for (int i = 1; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c) && !Character.isWhitespace(name.charAt(i - 1))) {
                return true;
            }
        }
        return false;
    }

}