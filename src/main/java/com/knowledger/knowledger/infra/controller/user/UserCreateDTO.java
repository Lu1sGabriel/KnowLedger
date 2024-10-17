package com.knowledger.knowledger.infra.controller.user;

import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO(
        @NotBlank
        String name,
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank
        String confirmedPassword) {
}
