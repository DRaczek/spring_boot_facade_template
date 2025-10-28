package draczek.facadetemplate.auth.command.domain;

import draczek.facadetemplate.auth.command.dto.RegistrationDto;
import draczek.facadetemplate.user.domain.command.User;
import draczek.facadetemplate.user.domain.command.UserFacade;
import draczek.facadetemplate.userActionToken.domain.command.UserActionToken;
import draczek.facadetemplate.userActionToken.domain.command.UserActionTokenFacade;
import draczek.facadetemplate.userActionToken.domain.enumerated.UserActionTokenEnum;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

/**
 * Class for user registration related actions.
 */
@RequiredArgsConstructor
class RegistrationUseCase {

  private final RegistrationValidationHelper validationHelper;
  private final UserFacade userFacade;
  private final UserActionTokenFacade userActionTokenFacade;
  private final SendMailUseCase sendMailUseCase;

  /**
   * Registering new user method
   */
  public void registration(@NotNull RegistrationDto dto) {
    validationHelper.validate(dto);
    registerUser(dto);
  }

  private void registerUser(RegistrationDto dto) {
    User user = createUser(dto);
    createAndSendActiveToken(user);
  }

  private User createUser(RegistrationDto dto) {
    return userFacade.create(dto);
  }

  private void createAndSendActiveToken(User user) {
    UserActionToken userActionToken = userActionTokenFacade.create(
        user,
        UserActionTokenEnum.ACTIVATE_ACCOUNT);
    sendMailUseCase.sendActivateAccountMail(userActionToken);
  }
}
