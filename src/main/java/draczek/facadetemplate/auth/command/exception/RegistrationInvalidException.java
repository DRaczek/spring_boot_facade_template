package draczek.facadetemplate.auth.command.exception;


import draczek.facadetemplate.common.dto.ViolationDto;
import draczek.facadetemplate.common.exception.DtoInvalidException;
import java.util.List;

/**
 * Invalid RegistrationDto exception.
 */
public class RegistrationInvalidException extends DtoInvalidException {
  private static final String DEFAULT_CODE = "registration_validation_failed";

  public RegistrationInvalidException(Object dto, List<ViolationDto> violations) {
    super(dto, violations, DEFAULT_CODE);
  }
}
