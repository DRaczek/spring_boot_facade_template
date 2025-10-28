package draczek.facadetemplate.auth.command.domain;

import draczek.facadetemplate.auth.command.exception.StepTwoResetPasswordNotValidException;
import draczek.facadetemplate.common.dto.ViolationDto;
import draczek.facadetemplate.infrastructure.security.domain.command.SecurityFacade;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

/**
 * Helper class for StepTwoPasswordResetDto validation.
 */
@RequiredArgsConstructor
class StepTwoResetPasswordValidationHelper {
  private final SecurityFacade securityFacade;

  /**
   * Helper method for StepTwoPasswordResetDto validation.
   *
   * @param password        new password
   * @param confirmPassword new password repeated
   */
  public void validate(String password, String confirmPassword) {
    List<ViolationDto> violations = new ArrayList<>(
        securityFacade.validatePassword(password, confirmPassword));

    if (!violations.isEmpty()) {
      throw new StepTwoResetPasswordNotValidException(password, violations);
    }
  }
}
