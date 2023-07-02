package org.kappa.server.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record User(
    @NotBlank(message = "Username must not be null.")
    @Size(max = 15, message = "Maximum username length is 15.")
    String username,
    @NotBlank(message = "Password must not be null.")
    @Size(min = 5, message = "Minimum password length is 5.")
    String password
) {
}
