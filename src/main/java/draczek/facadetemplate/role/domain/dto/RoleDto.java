package draczek.facadetemplate.role.domain.dto;

import draczek.facadetemplate.role.domain.enumerated.RoleEnum;
import java.util.UUID;

/**
 * Role entity's dto.
 */
public record RoleDto(UUID uuid, RoleEnum name) {
}