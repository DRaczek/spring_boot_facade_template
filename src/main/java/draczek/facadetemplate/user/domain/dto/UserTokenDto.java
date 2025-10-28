package draczek.facadetemplate.user.domain.dto;

import static draczek.facadetemplate.user.domain.command.WebSecurityAuthTokenFilter.BEARER;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Value;

/**
 * User authentication token dto.
 */
@Value
public class UserTokenDto {
  UUID uuid;

  String username;

  List<String> roles;

  String token;

  Date expirationDate;

  String type = BEARER;
}
