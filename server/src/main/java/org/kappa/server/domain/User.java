package org.kappa.server.domain;

import jakarta.validation.constraints.NotBlank;


public record User(
    @NotBlank(message = "Username must not be null.") String username,
    @NotBlank(message = "Password must not be null.") String password
) {
}
