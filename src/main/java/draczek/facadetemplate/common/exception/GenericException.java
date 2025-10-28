package draczek.facadetemplate.common.exception;

import lombok.Getter;

/**
 * Generic exception for this project.
 */
@Getter
public class GenericException extends RuntimeException {

  private final String rootCode = "exception.";
  private final String code;

  public GenericException(String code) {
    super();
    this.code = code;
  }

  public GenericException(String message, String code) {
    super(message);
    this.code = code;
  }

  public GenericException(String message, String code, Throwable cause) {
    super(message, cause);
    this.code = code;
  }
}