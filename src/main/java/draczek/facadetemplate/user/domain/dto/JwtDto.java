package draczek.facadetemplate.user.domain.dto;

import java.util.Date;
import lombok.Value;

/**
 * JWT token's dto.
 */
@Value
public class JwtDto {
  String token;
  Date expiration;
}
