package draczek.facadetemplate.infrastructure.security.domain.command;

import draczek.facadetemplate.common.dto.ViolationDto;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

/**
 * Helper class for password validation.
 */
@RequiredArgsConstructor
class PasswordValidationHelper {

  /**
   * Helper method for password validation.
   *
   * @param password        Password
   * @param confirmPassword Repeated Password
   * @return violations
   */
  public List<ViolationDto> validate(@NotBlank String password, @NotBlank String confirmPassword) {
    List<ViolationDto> violations = new ArrayList<>();

    ViolationDto violationDto = validateStrongPassword(password);
    if (violationDto != null) {
      violations.add(violationDto);
    }

    violationDto = validateConfirmPassword(password, confirmPassword);
    if (violationDto != null) {
      violations.add(violationDto);
    }

    return violations;
  }

  private ViolationDto validateStrongPassword(String password) {
    if (isPasswordStrong(password)) {
      return null;
    } else {
      return new ViolationDto(
          "password_not_strong_enough",
          "password",
          "Password is not strong enough");
    }
  }

  private boolean isPasswordStrong(String password) {
    String regex = "^(?=.*[a-z])(?=.*[A-Z])" // at least 1 small letter and 1 big letter
        + "(?=.*[0-9])" // at least 1 digit
        + "(?=.*[@$!%*#?&])" // at least 1 special character
        + "(?=\\S+$).{8,20}$"; //the last character can't be a whitespace
    return Pattern.compile(regex).matcher(password).matches();
  }

  private ViolationDto validateConfirmPassword(String password, String confirmPassword) {
    if (password.equals(confirmPassword)) {
      return null;
    } else {
      return new ViolationDto(
          "confirmPassword_not_matching",
          "confirmPassword",
          "Passwords do not match");
    }
  }
}
