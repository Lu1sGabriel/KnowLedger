package com.knowledger.knowledger.commom.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public final class PasswordService {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*(),.?\":{}|<>";

    private static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern
            .compile("[" + Pattern.quote(SPECIAL_CHARACTERS) + "]");
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d");
    private static final Pattern INVALID_CHAR_PATTERN = Pattern.compile("[çÇ]");

    private enum PasswordError {
        PASSWORD_NULL("A senha não pode ser nula."),
        PASSWORD_LENGTH("A senha deve ter no mínimo " + MIN_PASSWORD_LENGTH + " caracteres."),
        PASSWORD_SPECIAL_CHAR("A senha deve conter pelo menos um caractere especial."),
        PASSWORD_UPPERCASE("A senha deve conter pelo menos uma letra maiúscula."),
        PASSWORD_DIGIT("A senha deve conter pelo menos um número."),
        PASSWORD_REPEATED_CHAR("A senha não pode conter mais de três caracteres repetidos consecutivos."),
        PASSWORD_INVALID_CHAR("A senha não pode conter caracteres inválidos como 'ç'.");

        private final String message;

        PasswordError(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean arePasswordsEqual(String password, String confirmPassword) {
        if (password == null || confirmPassword == null) {
            return false;
        }
        return password.equals(confirmPassword);
    }


    public String validatePassword(String password) {
        if (password == null) {
            return PasswordError.PASSWORD_NULL.getMessage();
        }

        if (isShorterThanMinimumLength(password)) {
            return PasswordError.PASSWORD_LENGTH.getMessage();
        }

        if (!containsSpecialCharacter(password)) {
            return PasswordError.PASSWORD_SPECIAL_CHAR.getMessage();
        }

        if (!containsUppercase(password)) {
            return PasswordError.PASSWORD_UPPERCASE.getMessage();
        }

        if (!containsDigit(password)) {
            return PasswordError.PASSWORD_DIGIT.getMessage();
        }

        if (containsInvalidCharacter(password)) {
            return PasswordError.PASSWORD_INVALID_CHAR.getMessage();
        }

        if (hasMoreThanThreeConsecutiveRepeatedChars(password)) {
            return PasswordError.PASSWORD_REPEATED_CHAR.getMessage();
        }

        return null;
    }

    private boolean isShorterThanMinimumLength(String password) {
        return password.length() < MIN_PASSWORD_LENGTH;
    }

    private boolean containsSpecialCharacter(String password) {
        return SPECIAL_CHARACTER_PATTERN.matcher(password).find();
    }

    private boolean containsUppercase(String password) {
        return UPPERCASE_PATTERN.matcher(password).find();
    }

    private boolean containsDigit(String password) {
        return DIGIT_PATTERN.matcher(password).find();
    }

    private boolean containsInvalidCharacter(String password) {
        return INVALID_CHAR_PATTERN.matcher(password).find();
    }

    private boolean hasMoreThanThreeConsecutiveRepeatedChars(String password) {
        var normalizedPassword = password.toLowerCase();
        var charGroups = normalizedPassword.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        return charGroups.values().stream().anyMatch(count -> count > 3);
    }

}