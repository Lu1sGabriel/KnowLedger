package com.knowledger.knowledger.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.knowledger.knowledger.infra.persistence.user.UserEntity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class EmailValidatorTest {

    private Validator validator;
    private UserEntity defaultUser;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        defaultUser = new UserEntity(
                1L,
                "Luis Gabriel",
                "luis.gabriel@example.com",
                "SenhaForte1!",
                LocalDateTime.now(),
                true);
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
    void testInvalidEmailDomain() {
        defaultUser.setEmail("luis@notallowed.com");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(defaultUser);

        assertFalse(violations.isEmpty(), "Esperava uma violação para domínio de email não permitido.");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("Domínio de email não permitido.")));
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