package draczek.facadetemplate.user.domain.exception;

import draczek.facadetemplate.common.dto.ViolationDto;
import draczek.facadetemplate.common.exception.DtoInvalidException;
import java.util.List;

/**
 * Invalid UpdateUserDto exception.
 */
public class UpdateUserInvalidException extends DtoInvalidException {
  private static final String DEFAULT_CODE = "user_update_validation_failed";

  public UpdateUserInvalidException(Object dto, List<ViolationDto> violations) {
    super(dto, violations, DEFAULT_CODE);
  }
}
