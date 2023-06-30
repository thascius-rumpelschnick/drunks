package org.kappa.server.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record UserData(
    @NotBlank String username,
    @Min(0) int highScore,
    @NotNull Level level
) {
}