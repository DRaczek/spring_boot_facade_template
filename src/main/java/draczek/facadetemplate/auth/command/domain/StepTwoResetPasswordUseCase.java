package draczek.facadetemplate.auth.command.domain;

import draczek.facadetemplate.auth.command.dto.ResetPasswordStepTwoDto;
import draczek.facadetemplate.user.domain.command.UserFacade;
import draczek.facadetemplate.userActionToken.domain.command.UserActionToken;
import draczek.facadetemplate.userActionToken.domain.command.UserActionTokenFacade;
import draczek.facadetemplate.userActionToken.domain.enumerated.UserActionTokenDeleteCauseEnum;
import draczek.facadetemplate.userActionToken.domain.enumerated.UserActionTokenEnum;
import lombok.RequiredArgsConstructor;

/**
 * Class for stuff related do step two of password reset process.
 */
@RequiredArgsConstructor
class StepTwoResetPasswordUseCase {
  private final StepTwoResetPasswordValidationHelper validationHelper;
  private final UserFacade userFacade;
  private final UserActionTokenFacade userActionTokenFacade;

  /**
   * Method for step two of password reset process.
   */
  public void reset(ResetPasswordStepTwoDto dto) {
    UserActionToken userActionToken = userActionTokenFacade.get(dto.getKey(),
        UserActionTokenEnum.RESET_PASSWORD);
    validationHelper.validate(dto.getPassword(), dto.getConfirmPassword());
    userFacade.changePassword(userActionToken.getUser(), dto.getPassword());
    userActionTokenFacade.delete(userActionToken, UserActionTokenDeleteCauseEnum.USED);
  }
}
