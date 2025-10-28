package draczek.facadetemplate.auth.command.exception;

import draczek.facadetemplate.common.dto.ViolationDto;
import draczek.facadetemplate.common.exception.DtoInvalidException;
import java.util.List;

/**
 * Step two password reset process validation failed exception.
 */
public class StepTwoResetPasswordNotValidException extends DtoInvalidException {
  private static final String DEFAULT_CODE = "step_two_reset_password_validation_failed";

  public StepTwoResetPasswordNotValidException(Object dto, List<ViolationDto> violations) {
    super(dto, violations, DEFAULT_CODE);
  }
}
