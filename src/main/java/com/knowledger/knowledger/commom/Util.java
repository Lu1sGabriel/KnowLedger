package com.knowledger.knowledger.commom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Pattern;

public class Util {

    private Util() {
    }

    static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public static String passwordValidate(String password) {

        final int MIN_PASSWORD_LENGTH = 8;
        final String SPECIAL_CHARACTERS = "!@#$%^&*(),.?\":{}|<>";

        final String PASSWORD_NULL_ERROR_MESSAGE = "A senha não pode ser nula.";
        final String PASSWORD_LENGTH_ERROR_MESSAGE = "A senha deve ter no mínimo " + MIN_PASSWORD_LENGTH
                + " caracteres.";
        final String PASSWORD_SPECIAL_CHAR_ERROR_MESSAGE = "A senha deve conter pelo menos um caractere especial.";
        final String PASSWORD_UPPERCASE_ERROR_MESSAGE = "A senha deve conter pelo menos uma letra maiúscula.";
        final String PASSWORD_DIGIT_ERROR_MESSAGE = "A senha deve conter pelo menos um número.";
        final String PASSWORD_REPEATED_CHAR_ERROR_MESSAGE = "A senha não pode conter mais de três caracteres repetidos consecutivos.";
        final String PASSWORD_INVALID_CHAR_ERROR_MESSAGE = "A senha não pode conter caracteres inválidos como 'ç'.";

        final Pattern SPECIAL_CHARACTER_PATTERN = Pattern
                .compile("[" + Pattern.quote(SPECIAL_CHARACTERS) + "]");
        final Pattern UPPERCASE_PATTERN = Pattern.compile("[A-Z]");
        final Pattern DIGIT_PATTERN = Pattern.compile("\\d");

        if (password == null) {
            return PASSWORD_NULL_ERROR_MESSAGE;
        }

        if (password.length() < MIN_PASSWORD_LENGTH) {
            return PASSWORD_LENGTH_ERROR_MESSAGE;
        }

        if (!SPECIAL_CHARACTER_PATTERN.matcher(password).find()) {
            return PASSWORD_SPECIAL_CHAR_ERROR_MESSAGE;
        }

        if (!UPPERCASE_PATTERN.matcher(password).find()) {
            return PASSWORD_UPPERCASE_ERROR_MESSAGE;
        }

        if (!DIGIT_PATTERN.matcher(password).find()) {
            return PASSWORD_DIGIT_ERROR_MESSAGE;
        }

        if (containsInvalidCharacters(password)) {
            return PASSWORD_INVALID_CHAR_ERROR_MESSAGE;
        }

        if (containsMoreThanThreeRepeatedCharacters(password)) {
            return PASSWORD_REPEATED_CHAR_ERROR_MESSAGE;
        }

        return null;
    }

    private static boolean containsMoreThanThreeRepeatedCharacters(String password) {
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

    private static boolean containsInvalidCharacters(String password) {
        final Pattern INVALID_CHAR_PATTERN = Pattern.compile("[çÇ]");
        return INVALID_CHAR_PATTERN.matcher(password).find();
    }

}
