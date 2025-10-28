package draczek.facadetemplate.role.domain.command;

import draczek.facadetemplate.role.domain.enumerated.RoleEnum;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * Role package's facade.
 */
@Validated
@RequiredArgsConstructor
@Transactional
public class RoleFacade {
  private final SearchRoleUseCase searchRoleUseCase;

  /**
   * Service for fetching Role entity based on associated RoleEnum.
   *
   * @param roleEnum role enum
   * @return Role entity
   */
  public Role getEntity(@NotNull RoleEnum roleEnum) {
    return searchRoleUseCase.getEntity(roleEnum);
  }
}
