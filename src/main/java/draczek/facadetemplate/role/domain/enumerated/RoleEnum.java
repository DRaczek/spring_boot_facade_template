package draczek.facadetemplate.role.domain.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Role enum.
 */
@Getter
@RequiredArgsConstructor
public enum RoleEnum {
  ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN"),
  ROLE_ADMIN("ROLE_ADMIN"),
  ROLE_USER("ROLE_USER");

  private final String value;
}