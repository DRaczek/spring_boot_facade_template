package draczek.facadetemplate.auth.command.domain;

import draczek.facadetemplate.infrastructure.security.domain.command.SecurityFacade;
import draczek.facadetemplate.user.domain.command.JwtUtils;
import draczek.facadetemplate.user.domain.command.UserFacade;
import draczek.facadetemplate.userActionToken.domain.command.UserActionTokenFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * Authentication Facade's config class.
 */
@Configuration
class AuthenticationConfig {
  @Bean
  AuthenticationFacade authenticationFacade(
      AuthenticationManager authenticationManager,
      JwtUtils jwtUtils,
      UserFacade userFacade,
      UserActionTokenFacade userActionTokenFacade,
      JavaMailSender emailSender,
      SecurityFacade securityFacade) {

    LoginUseCase loginUseCase = new LoginUseCase(
        jwtUtils,
        authenticationManager,
        userFacade);

    SendMailUseCase sendMailUseCase = new SendMailUseCase(emailSender);

    RegistrationValidationHelper registrationValidationHelper = new RegistrationValidationHelper(
        securityFacade,
        userFacade);
    RegistrationUseCase registrationUseCase = new RegistrationUseCase(
        registrationValidationHelper,
        userFacade,
        userActionTokenFacade,
        sendMailUseCase);

    ActivationUseCase activationUseCase = new ActivationUseCase(
        userActionTokenFacade,
        userFacade);

    StepOneResetPasswordUseCase stepOneResetPasswordUseCase = new StepOneResetPasswordUseCase(
        userFacade,
        userActionTokenFacade,
        sendMailUseCase
    );

    StepTwoResetPasswordValidationHelper stepTwoResetPasswordValidationHelper
        = new StepTwoResetPasswordValidationHelper(securityFacade);
    StepTwoResetPasswordUseCase stepTwoResetPasswordUseCase = new StepTwoResetPasswordUseCase(
        stepTwoResetPasswordValidationHelper,
        userFacade,
        userActionTokenFacade);

    RefreshUserTokenUseCase refreshUserTokenUseCase = new RefreshUserTokenUseCase(
        userFacade,
        jwtUtils);

    return new AuthenticationFacade(
        loginUseCase,
        registrationUseCase,
        activationUseCase,
        stepOneResetPasswordUseCase,
        stepTwoResetPasswordUseCase,
        refreshUserTokenUseCase,
        userFacade);
  }
}
