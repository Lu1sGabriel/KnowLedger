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

public class NameValidatorTest {

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
            factory.close();
        }
    }

    @Test
    void testValidSingleName() {
        defaultUser.setName("Luis");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(defaultUser);

        assertTrue(violations.isEmpty(), "Esperava nenhuma violação para um nome válido.");
    }

    @Test
    void testValidCompositeNameWithSpaces() {
        defaultUser.setName("Luis Gabriel");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(defaultUser);

        assertTrue(violations.isEmpty(), "Esperava nenhuma violação para um nome composto válido.");
    }

    @Test
    void testInvalidNameWithoutSpaces() {
        defaultUser.setName("LuisGabriel");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(defaultUser);

        assertFalse(violations.isEmpty(), "Esperava uma violação para um nome composto sem espaços.");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("Nomes compostos devem conter espaços entre as palavras.")));
    }

    @Test
    void testNameWithSpecialCharacters() {
        defaultUser.setName("Luis@");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(defaultUser);

        assertFalse(violations.isEmpty(), "Esperava uma violação para nome com caracteres especiais.");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("O nome não pode conter caracteres especiais.")));
    }

    @Test
    void testNameWithNumbers() {
        defaultUser.setName("Luis123");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(defaultUser);

        assertFalse(violations.isEmpty(), "Esperava uma violação para nome com números.");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("O nome deve conter apenas letras e espaços.")));
    }

}