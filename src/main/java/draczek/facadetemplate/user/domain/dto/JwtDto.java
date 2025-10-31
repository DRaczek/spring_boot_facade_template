package draczek.facadetemplate.user.domain.dto;

import java.time.LocalDateTime;
import lombok.Value;

/**
 * JWT token's dto.
 */
@Value
public class JwtDto {
  String token;
  LocalDateTime expiration;
}