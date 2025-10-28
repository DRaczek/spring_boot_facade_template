package draczek.facadetemplate.user.domain.dto;

import draczek.facadetemplate.role.domain.dto.RoleDto;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

/**
 * User's entity dto.
 */
@Value
public class UserDto {

  UUID uuid;

  AccountDto account;

  String username;

  boolean enabled;

  Set<RoleDto> roles = new HashSet<>();

  @NotNull
  Integer version;
}
