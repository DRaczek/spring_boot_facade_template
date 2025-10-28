package draczek.facadetemplate.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Optimistic lock version validation exception.
 */
@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
@Getter
public class OptimisticLockException extends GenericException {

  private static final String DEFAULT_CODE = "optimistic_lock_exception";

  public OptimisticLockException() {
    super("Entity's version is invalid", DEFAULT_CODE);
  }

  public OptimisticLockException(String message, String code) {
    super(message, code);
  }
}