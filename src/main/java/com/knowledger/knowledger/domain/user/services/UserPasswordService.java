package com.knowledger.knowledger.domain.user.services;

import com.knowledger.knowledger.infra.exceptions.BusinessException;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public final class UserPasswordService implements IUserPasswordService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*(),.?\":{}|<>";

    private static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern
            .compile("[" + Pattern.quote(SPECIAL_CHARACTERS) + "]");
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d");
    private static final Pattern INVALID_CHAR_PATTERN = Pattern.compile("[çÇ]");

    private enum PasswordError {
        PASSWORD_NULL("Ambas as senhas devem ser preenchidas."),
        PASSWORD_LENGTH("A senha deve ter no mínimo " + MIN_PASSWORD_LENGTH + " caracteres."),
        PASSWORD_SPECIAL_CHAR("A senha deve conter pelo menos um caractere especial."),
        PASSWORD_UPPERCASE("A senha deve conter pelo menos uma letra maiúscula."),
        PASSWORD_DIGIT("A senha deve conter pelo menos um número."),
        PASSWORD_REPEATED_CHAR("A senha não pode conter mais de três caracteres repetidos consecutivos."),
        PASSWORD_INVALID_CHAR("A senha não pode conter caracteres inválidos como 'ç'."),
        PASSWORD_NOT_EQUAL("As senhas não são iguais."),
        ENCODE_ERROR("Erro inesperado. Por favor, entre em contato com o suporte de TI.");

        private final String message;

        PasswordError(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    @Override
    public String encode(String password) {
        try {
            return passwordEncoder.encode(password);
        } catch (Exception exception) {
            // Erro inesperado ao codificar a senha - erro interno no servidor (500)
            throw new BusinessException(PasswordError.ENCODE_ERROR.getMessage(), exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void validate(String password, String confirmPassword) {
        if (Objects.isNull(password) || Objects.isNull(confirmPassword)) {
            throw new BusinessException(PasswordError.PASSWORD_NULL.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (!arePasswordsEqual(password, confirmPassword)) {
            throw new BusinessException(PasswordError.PASSWORD_NOT_EQUAL.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (isShorterThanMinimumLength(password)) {
            throw new BusinessException(PasswordError.PASSWORD_LENGTH.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (!containsSpecialCharacter(password)) {
            throw new BusinessException(PasswordError.PASSWORD_SPECIAL_CHAR.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (!containsUppercase(password)) {
            throw new BusinessException(PasswordError.PASSWORD_UPPERCASE.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (!containsDigit(password)) {
            throw new BusinessException(PasswordError.PASSWORD_DIGIT.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (containsInvalidCharacter(password)) {
            throw new BusinessException(PasswordError.PASSWORD_INVALID_CHAR.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (hasMoreThanThreeConsecutiveRepeatedChars(password)) {
            throw new BusinessException(PasswordError.PASSWORD_REPEATED_CHAR.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private boolean arePasswordsEqual(String password, String confirmPassword) {
        return password != null && password.equals(confirmPassword);
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