package draczek.facadetemplate.common.exception.handler;

import draczek.facadetemplate.common.dto.DefaultErrorResponseDto;
import draczek.facadetemplate.common.dto.ValidationErrorResponseDto;
import draczek.facadetemplate.common.dto.ViolationDto;
import draczek.facadetemplate.common.exception.DtoInvalidException;
import draczek.facadetemplate.common.exception.EntityNotFoundException;
import draczek.facadetemplate.common.exception.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Custom exceptions' handler.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
class GenericExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String VALIDATION_FAILED_CODE =
      DefaultExceptionHandler.PREFIX + "validation_failed";

  /**
   * Validation exception handler.
   */
  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<Object> handleValidationExceptions(ValidationException exception) {
    log.error(exception.getMessage(), exception);
    Throwable cause = exception.getCause();
    if (cause instanceof EntityNotFoundException) {
      return handleEntityNotFoundExceptions((EntityNotFoundException) cause);
    }
    if (cause instanceof DtoInvalidException) {
      return handleDtoInvalidExceptions((DtoInvalidException) cause);
    }
    if (cause instanceof OptimisticLockException) {
      return handleOptimisticLockExceptions((OptimisticLockException) cause);
    }
    if (cause instanceof ConstraintViolationException) {
      return handleConstraintViolations((ConstraintViolationException) cause);
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Invalid Dto exception handler.
   */
  @ExceptionHandler(DtoInvalidException.class)
  public ResponseEntity<Object> handleDtoInvalidExceptions(DtoInvalidException ex) {
    log.error(ex.getMessage(), ex);
    return new ResponseEntity<>(ValidationErrorResponseDto.builder()
        .title("Your request parameters are invalid")
        .code(ex.getRootCode() + ex.getCode())
        .timestamp(LocalDateTime.now())
        .violations(getValidationErrors(ex)).build(),
        HttpStatus.BAD_REQUEST);
  }

  /**
   * Constraint violation exception handler.
   */
  @ExceptionHandler(value = ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolations(ConstraintViolationException ex) {
    log.error(ex.getMessage(), ex);
    return new ResponseEntity<>(ValidationErrorResponseDto.builder()
        .title("Your request parameters are invalid")
        .code(VALIDATION_FAILED_CODE)
        .timestamp(LocalDateTime.now())
        .violations(getValidationErrors(ex)).build(),
        HttpStatus.BAD_REQUEST);
  }

  /**
   * Entity not found exception handler.
   */
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Object> handleEntityNotFoundExceptions(EntityNotFoundException ex) {
    log.error(ex.getMessage(), ex);
    return new ResponseEntity<>(DefaultErrorResponseDto.builder()
        .title("Object was not found for given parameters")
        .code(ex.getRootCode() + ex.getCode())
        .timestamp(LocalDateTime.now()).build(),
        HttpStatus.NOT_FOUND);
  }

  /**
   * Invalid entity version exception handler.
   */
  @ExceptionHandler({
      OptimisticLockException.class,
      StaleObjectStateException.class,
      jakarta.persistence.OptimisticLockException.class})
  public ResponseEntity<Object> handleOptimisticLockExceptions(OptimisticLockException ex) {
    log.error(ex.getMessage(), ex);
    return new ResponseEntity<>(DefaultErrorResponseDto.builder()
        .title("Object was updated or deleted by another transaction")
        .code(ex.getRootCode() + ex.getCode())
        .timestamp(LocalDateTime.now()).build(),
        HttpStatus.PRECONDITION_FAILED);
  }

  private List<ViolationDto> getValidationErrors(DtoInvalidException ex) {
    return ex.getViolations()
        .stream()
        .map(v -> new ViolationDto(DefaultExceptionHandler.PREFIX + v.code(), v.name(), v.reason()))
        .collect(Collectors.toList());
  }

  private List<ViolationDto> getValidationErrors(
      ConstraintViolationException ex) {
    List<ViolationDto> errors = new ArrayList<>();
    ex.getConstraintViolations().forEach(e -> errors.add(extractValidationError(e)));
    return errors;
  }

  private ViolationDto extractValidationError(ConstraintViolation<?> e) {
    String fieldName = getPropertyName(e.getPropertyPath().toString());
    return new ViolationDto(DefaultExceptionHandler.PREFIX
        + e.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName(),
        fieldName, e.getMessage());
  }

  private String getPropertyName(String propertyPath) {
    String[] propertiesFragments = propertyPath.split("\\.");
    return propertiesFragments[propertiesFragments.length - 1];
  }
}