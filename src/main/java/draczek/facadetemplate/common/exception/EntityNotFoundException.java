package draczek.facadetemplate.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * NotFound exception implementation.
 */
@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends GenericException {

  private static final String DEFAULT_CODE = "entity_was_not_found";

  public EntityNotFoundException(String message) {
    super(message, DEFAULT_CODE);
  }

  public EntityNotFoundException(String message, String code) {
    super(message, code);
  }
}