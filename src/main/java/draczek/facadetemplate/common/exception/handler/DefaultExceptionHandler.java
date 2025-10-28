package draczek.facadetemplate.common.exception.handler;

import draczek.facadetemplate.common.dto.DefaultErrorResponseDto;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Default exception handling class.
 */
@RestControllerAdvice
@Slf4j
class DefaultExceptionHandler extends ResponseEntityExceptionHandler {
  static final String PREFIX = "exception|";
  private static final String BAD_CREDENTIALS = PREFIX + "bad_credentials_error";
  private static final String ACCESS_DENIED = PREFIX + "access_denied";
  private static final String UNSUPPORTED_OPERATION = PREFIX + "unsupported_operation";
  private static final String INTERNAL_SERVER_ERROR = PREFIX + "internal_server_error";

  /**
   * Method for handling bad credentials' exception.
   */
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Object> handleBadCredentialsExceptions(Exception ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    DefaultErrorResponseDto response = DefaultErrorResponseDto.builder()
        .title("Bad Credentials")
        .code(BAD_CREDENTIALS)
        .timestamp(LocalDateTime.now())
        .build();
    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }

  /**
   * Method for handling access denied exceptions.
   */
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Object> handleAccessDeniedExceptions(AccessDeniedException ex,
                                                             WebRequest request) {
    log.error(ex.getMessage(), ex);
    DefaultErrorResponseDto response = DefaultErrorResponseDto.builder()
        .title("Access denied")
        .code(ACCESS_DENIED)
        .timestamp(LocalDateTime.now())
        .build();
    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
  }

  /**
   * Method for handling unsupported operations exceptions, that is bad requests.
   */
  @ExceptionHandler(UnsupportedOperationException.class)
  public ResponseEntity<Object> handleUnsupportedOperationExceptions(
      UnsupportedOperationException ex,
      WebRequest request) {
    log.error(ex.getMessage(), ex);
    DefaultErrorResponseDto response = DefaultErrorResponseDto.builder()
        .title("Unsupported operation")
        .code(UNSUPPORTED_OPERATION)
        .timestamp(LocalDateTime.now())
        .build();
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * Method for handling all unsupported exceptions.
   */
  @ExceptionHandler(Throwable.class)
  public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    DefaultErrorResponseDto response = DefaultErrorResponseDto.builder()
        .title("Internal Server Error")
        .code(INTERNAL_SERVER_ERROR)
        .timestamp(LocalDateTime.now())
        .build();
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}