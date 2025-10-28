package draczek.facadetemplate.auth.command.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

/**
 * Step two of password reset process dto.
 */
@Value
public class ResetPasswordStepTwoDto {
  @NotBlank
  String key;

  @NotBlank
  String password;

  @NotBlank
  String confirmPassword;

}
