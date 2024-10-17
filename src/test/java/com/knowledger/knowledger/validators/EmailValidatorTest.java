package com.knowledger.knowledger.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.knowledger.knowledger.infra.persistence.user.UserEntity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class EmailValidatorTest {

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
                "luis.gabriel@example.com",
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
    void testValidEmail() {
        defaultUser.setEmail("luis@gmail.com");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(defaultUser);

        assertTrue(violations.isEmpty(), "Esperava nenhuma violação para um email válido.");
    }

    @Test
    void testInvalidEmailFormat() {
        defaultUser.setEmail("luisgmail.com");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(defaultUser);

        assertFalse(violations.isEmpty(), "Esperava uma violação para formato de email inválido.");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("Formato de email inválido.")));
    }

    @Test
    void testNullEmail() {
        defaultUser.setEmail(null);

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(defaultUser);

        assertFalse(violations.isEmpty(), "Esperava uma violação para email nulo.");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("Email não pode ser nulo.")));
    }

}