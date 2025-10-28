package draczek.facadetemplate.auth.command.domain;

import draczek.facadetemplate.auth.command.dto.RegistrationDto;
import draczek.facadetemplate.auth.command.exception.RegistrationInvalidException;
import draczek.facadetemplate.common.dto.ViolationDto;
import draczek.facadetemplate.infrastructure.security.domain.command.SecurityFacade;
import draczek.facadetemplate.user.domain.command.UserFacade;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

/**
 * Helper class for Registration data validation.
 */
@RequiredArgsConstructor
class RegistrationValidationHelper {
  private final SecurityFacade securityFacade;
  private final UserFacade userFacade;

  /**
   * Method for RegistrationDto validation.
   *
   * @param dto RegistrationDto
   * @throws RegistrationInvalidException violations
   */
  public void validate(@NotNull RegistrationDto dto) throws RegistrationInvalidException {
    List<ViolationDto> violations = new ArrayList<>(
        securityFacade.validatePassword(dto.getPassword(), dto.getConfirmPassword()));

    violations.addAll(userFacade.validate(dto.getUsername()));

    if (!violations.isEmpty()) {
      throw new RegistrationInvalidException(dto, violations);
    }
  }
}
