package com.knowledger.knowledger.infra.validations.validators;

import com.knowledger.knowledger.infra.validations.annotations.Name;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<Name, String> {

    private static final String SPECIAL_CHARACTERS = "!@#$%&*()_+=-[]{};:/<>?|\"\\,.";
    private static final String NAME_NULL_ERROR_MESSAGE = "O nome não pode ser nulo.";
    private static final String NAME_FORMAT_ERROR_MESSAGE = "O nome deve conter apenas letras e espaços.";
    private static final String NAME_LENGTH_ERROR_MESSAGE = "O nome deve ter no mínimo 2 caracteres.";
    private static final String NAME_SPECIAL_CHAR_ERROR_MESSAGE = "O nome não pode conter caracteres especiais.";

    private static final int MIN_LENGTH = 2;

    @Override
    public void initialize(Name name) {
    }

    @Override
    public boolean isValid(String nameField, ConstraintValidatorContext cxt) {
        if (nameField == null) {
            throw new IllegalArgumentException(NAME_NULL_ERROR_MESSAGE);
        }
        validateNameLength(nameField);
        validateNameCharacters(nameField);

        return true;
    }

    private void validateNameLength(String nameField) {
        if (nameField.length() < MIN_LENGTH) {
            throw new IllegalArgumentException(NAME_LENGTH_ERROR_MESSAGE);
        }
    }

    private void validateNameCharacters(String nameField) {
        for (char c : nameField.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                throw new IllegalArgumentException(NAME_FORMAT_ERROR_MESSAGE);
            }
            if (SPECIAL_CHARACTERS.indexOf(c) != -1) {
                throw new IllegalArgumentException(NAME_SPECIAL_CHAR_ERROR_MESSAGE);
            }
        }
    }

}