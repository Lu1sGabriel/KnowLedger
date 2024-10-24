package com.knowledger.knowledger.domain.user.services;

import com.knowledger.knowledger.infra.exceptions.BusinessException;
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
    public String encode(String password) throws BusinessException {
        try {
            return passwordEncoder.encode(password);
        } catch (Exception exception) {
            throw new BusinessException(PasswordError.ENCODE_ERROR.getMessage(), exception);
        }
    }

    @Override
    public void validate(String password, String confirmPassword) throws BusinessException {
        if (Objects.isNull(password) || Objects.isNull(confirmPassword)) {
            throw new BusinessException(PasswordError.PASSWORD_NULL.getMessage());
        }

        if (!arePasswordsEqual(password, confirmPassword)) {
            throw new BusinessException(PasswordError.PASSWORD_NOT_EQUAL.getMessage());
        }

        if (isShorterThanMinimumLength(password)) {
            throw new BusinessException(PasswordError.PASSWORD_LENGTH.getMessage());
        }

        if (!containsSpecialCharacter(password)) {
            throw new BusinessException(PasswordError.PASSWORD_SPECIAL_CHAR.getMessage());
        }

        if (!containsUppercase(password)) {
            throw new BusinessException(PasswordError.PASSWORD_UPPERCASE.getMessage());
        }

        if (!containsDigit(password)) {
            throw new BusinessException(PasswordError.PASSWORD_DIGIT.getMessage());
        }

        if (containsInvalidCharacter(password)) {
            throw new BusinessException(PasswordError.PASSWORD_INVALID_CHAR.getMessage());
        }

        if (hasMoreThanThreeConsecutiveRepeatedChars(password)) {
            throw new BusinessException(PasswordError.PASSWORD_REPEATED_CHAR.getMessage());
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