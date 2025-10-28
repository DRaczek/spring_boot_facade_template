package draczek.facadetemplate.common.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Dto of validation violation.
 */
public record ViolationDto(@NotNull String code, @NotNull String name, @NotNull String reason) {

}