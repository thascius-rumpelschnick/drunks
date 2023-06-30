package org.kappa.server.persistence.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.kappa.server.domain.Level;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Document
public record UserData(
    @MongoId ObjectId id,
    @NotBlank String username,
    @Min(0) int highScore,
    @NotNull Level level
) {
}
