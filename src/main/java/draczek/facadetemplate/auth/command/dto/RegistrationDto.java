package draczek.facadetemplate.auth.command.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

/**
 * User registration dto.
 */
@Value
public class RegistrationDto {
  @NotBlank
  @Email
  String username;

  @NotBlank
  String password;

  @NotBlank
  String confirmPassword;
}
