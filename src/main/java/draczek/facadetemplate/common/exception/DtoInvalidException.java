package draczek.facadetemplate.common.exception;

import draczek.facadetemplate.common.dto.ViolationDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Invalid Dto exception.
 */
@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
@Slf4j
public class DtoInvalidException extends GenericException {
  private static final String DEFAULT_CODE = "invalid_dto";

  private final List<ViolationDto> violations;

  public DtoInvalidException(String code) {
    super("", code);
    this.violations = new ArrayList<>();
  }

  /**
   * Dto invalid exception constructor.
   *
   * @param object affected Dto
   * @param violations violations list
   */
  public DtoInvalidException(Object object, List<ViolationDto> violations) {
    super(String.format("%s is not valid", object.getClass().getSimpleName()), DEFAULT_CODE);
    this.violations = violations;
    violations.forEach(v -> log.error(v.toString()));
  }

  /**
   * Dto invalid exception constructor.
   *
   * @param object affected Dto
   * @param violations violations list
   * @param code exception code
   */
  public DtoInvalidException(Object object, List<ViolationDto> violations, String code) {
    super(String.format("%s is not valid", object.getClass().getSimpleName()), code);
    this.violations = violations;
    violations.forEach(v -> log.error(v.toString()));
  }
}