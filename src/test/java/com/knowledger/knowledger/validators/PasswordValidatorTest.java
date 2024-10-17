package com.knowledger.knowledger.validators;

import com.knowledger.knowledger.infra.persistence.user.UserEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordValidatorTest {

    private ValidatorFactory factory;
    private Validator validator;
    private UserEntity defaultUser;

    @BeforeEach
    void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        defaultUser = new UserEntity(
                1L,
                "Luis Gabriel",
                "luis.gabriel@gmail.com",
                "SenhaForte1!",
                true);
    }

    @AfterEach
    void tearDown() {
        if (factory != null) {
            factory.close();  // Fechando a ValidatorFactory para liberar os recursos
        }
    }

    @Test
    void testValidPassword() {
        // Modifica apenas o campo password para o caso de teste
        defaultUser.setPassword("NewPassword1!");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(defaultUser);

        assertTrue(violations.isEmpty(), "Esperava nenhuma violação para uma senha válida.");
    }

    @Test
    void testPasswordWithoutSpecialCharacters() {
        defaultUser.setPassword("Password123"); // Sem caracteres especiais

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(defaultUser);

        assertFalse(violations.isEmpty(), "Esperava uma violação para senha sem caracteres especiais.");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("A senha deve conter pelo menos um caractere especial.")));
    }

    @Test
    void testPasswordTooShort() {
        defaultUser.setPassword("P@ss1"); // Senha muito curta

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(defaultUser);

        assertFalse(violations.isEmpty(), "Esperava uma violação para senha muito curta.");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("A senha deve ter no mínimo 8 caracteres.")));
    }

    @Test
    void testPasswordWithoutUppercase() {
        defaultUser.setPassword("password123!");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(defaultUser);

        assertFalse(violations.isEmpty(), "Esperava uma violação para senha sem letras maiúsculas.");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("A senha deve conter pelo menos uma letra maiúscula.")));
    }

    @Test
    void testNullPassword() {
        defaultUser.setPassword(null);

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(defaultUser);

        assertFalse(violations.isEmpty(), "Esperava uma violação para senha nula.");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("A senha não pode ser nula.")));
    }

    @Test
    void testPasswordWithMoreThanThreeRepeatedCharacters() {
        defaultUser.setPassword("Passsssword1!");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(defaultUser);

        assertFalse(violations.isEmpty(), "Esperava uma violação para senha com mais de três caracteres repetidos.");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage()
                        .equals("A senha não pode conter mais de três caracteres repetidos consecutivos.")));
    }

    @Test
    void testPasswordWithThreeRepeatedCharacters() {
        defaultUser.setPassword("Passsword1!");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(defaultUser);

        assertTrue(violations.isEmpty(), "Esperava nenhuma violação para senha com até três caracteres repetidos.");
    }

    @Test
    void testPasswordWithInvalidCharacter() {
        defaultUser.setPassword("SenhaÇInvalida1!");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(defaultUser);

        assertFalse(violations.isEmpty(), "Esperava uma violação para senha contendo caracteres inválidos.");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("A senha não pode conter caracteres inválidos como 'ç'.")));
    }

}