package draczek.facadetemplate.auth.command.domain;

import draczek.facadetemplate.auth.command.dto.LoginDto;
import draczek.facadetemplate.auth.command.dto.RegistrationDto;
import draczek.facadetemplate.auth.command.dto.ResetPasswordStepTwoDto;
import draczek.facadetemplate.user.domain.command.UserFacade;
import draczek.facadetemplate.user.domain.dto.RefreshTokenDto;
import draczek.facadetemplate.user.domain.dto.UserTokenDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * Authentication's package facade.
 */
@Validated
@RequiredArgsConstructor
@Transactional
public class AuthenticationFacade {

  private final LoginUseCase loginUseCase;
  private final RegistrationUseCase registrationUseCase;
  private final ActivationUseCase activationUseCase;
  private final StepOneResetPasswordUseCase stepOneResetPasswordUseCase;
  private final StepTwoResetPasswordUseCase stepTwoResetPasswordUseCase;
  private final RefreshUserTokenUseCase refreshUserTokenUseCase;
  private final UserFacade userFacade;

  /**
   * Service for user login.
   *
   * @param dto LoginDto
   * @return User authentication token dto
   */
  public UserTokenDto login(@NotNull LoginDto dto) {
    return loginUseCase.login(dto);
  }

  /**
   * Service for user registration.
   *
   * @param dto RegistrationDto
   */
  public void register(@NotNull RegistrationDto dto) {
    registrationUseCase.registration(dto);
  }

  /**
   * Service for activation of new, inactive user.
   *
   * @param token token key
   */
  public void activate(@NotBlank String token) {
    activationUseCase.activate(token);
  }

  /**
   * Step one of password reset process.
   *
   * @param email user's email
   */
  public void stepOneResetPassword(@NotBlank @Email String email) {
    stepOneResetPasswordUseCase.reset(email);
  }

  public void stepTwoResetPassword(@NotNull ResetPasswordStepTwoDto dto) {
    stepTwoResetPasswordUseCase.reset(dto);
  }

  /**
   * Service for refreshing authentication token
   */
  public UserTokenDto refreshUserToken(@Valid @NotNull RefreshTokenDto dto) {
    return refreshUserTokenUseCase.refresh(dto);
  }

  /**
   * Logs out the user from the application by deleting the associated refresh token.
   *
   * @param dto dto containing refresh token etc.
   */
  public void logoutUser(@Valid @NotNull RefreshTokenDto dto) {
    userFacade.logout(dto.getToken());
  }

}
