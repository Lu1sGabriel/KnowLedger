package com.knowledger.knowledger.infra.controller.user;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record UserChangePasswordDTO(@NotBlank UUID userId,
                                    @NotBlank String token,
                                    @NotBlank String oldPassword,
                                    @NotBlank String newPassword,
                                    @NotBlank String confirmedNewPassword) {
}
