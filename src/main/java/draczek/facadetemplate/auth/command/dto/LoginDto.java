package draczek.facadetemplate.auth.command.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

/**
 * Login dto.
 */
@Value
public class LoginDto {

  @NotBlank
  String username;

  @NotBlank
  String password;
}
