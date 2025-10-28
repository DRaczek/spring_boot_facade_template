package draczek.facadetemplate.userActionToken.domain.command;

import draczek.facadetemplate.userActionToken.domain.enumerated.UserActionTokenEnum;
import draczek.facadetemplate.userActionToken.domain.exception.UserActionTokenNotFoundException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

/**
 * Class for UserActionToken validation.
 */
@RequiredArgsConstructor
class UserActionTokenValidationHelper {

  /**
   * Helper method for validation of UserActionToken.
   *
   * @param token UserActionToken instance
   * @param action action
   * @throws UserActionTokenNotFoundException UserActionToken not found exception
   */
  public void validate(UserActionToken token, UserActionTokenEnum action)
      throws UserActionTokenNotFoundException {

    if (token.getAction() != action) {
      throw new UserActionTokenNotFoundException(token.getKey());
    } else {
      LocalDateTime now = LocalDateTime.now();
      LocalDateTime expiredDate = token.getCreatedDate().plusHours(24);
      if (now.isAfter(expiredDate)) {
        throw new UserActionTokenNotFoundException(token.getKey());
      }
    }
  }
}
