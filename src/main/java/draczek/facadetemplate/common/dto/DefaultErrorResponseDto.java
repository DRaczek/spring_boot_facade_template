package draczek.facadetemplate.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Default dto for exception handling.
 */
@Value
@Builder
@EqualsAndHashCode
public class DefaultErrorResponseDto {
  String title;

  String code;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime timestamp;
}