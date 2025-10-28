package draczek.facadetemplate.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Dto for validation error handling.
 */
@Value
@Builder
@EqualsAndHashCode
public class ValidationErrorResponseDto {
  UUID uuid;

  @JsonProperty(value = "violations")
  List<ViolationDto> violations;

  String title;

  String code;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime timestamp;
}