package draczek.facadetemplate.auth.command.domain;

import draczek.facadetemplate.common.enumerated.StatusEnum;
import draczek.facadetemplate.user.domain.command.User;
import draczek.facadetemplate.user.domain.command.UserFacade;
import draczek.facadetemplate.userActionToken.domain.command.UserActionToken;
import draczek.facadetemplate.userActionToken.domain.command.UserActionTokenFacade;
import draczek.facadetemplate.userActionToken.domain.enumerated.UserActionTokenEnum;
import lombok.RequiredArgsConstructor;

/**
 * Class for stuff related do step one of password reset process.
 */
@RequiredArgsConstructor
class StepOneResetPasswordUseCase {
  private final UserFacade userFacade;
  private final UserActionTokenFacade userActionTokenFacade;
  private final SendMailUseCase sendMailUseCase;

  /**
   * Method for step one of password reset process.
   *
   * @param email User's email
   */
  public void reset(String email) {
    User user = userFacade.get(email, StatusEnum.ACTIVE);
    if (user != null) {
      createAndSendResetToken(user);
    }
  }

  private void createAndSendResetToken(User user) {
    UserActionToken userActionToken = userActionTokenFacade.create(
        user,
        UserActionTokenEnum.RESET_PASSWORD);
    sendMailUseCase.sendResetPasswordMail(userActionToken);
  }
}
