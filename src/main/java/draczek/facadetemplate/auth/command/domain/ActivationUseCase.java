package draczek.facadetemplate.auth.command.domain;

import draczek.facadetemplate.user.domain.command.UserFacade;
import draczek.facadetemplate.userActionToken.domain.command.UserActionToken;
import draczek.facadetemplate.userActionToken.domain.command.UserActionTokenFacade;
import draczek.facadetemplate.userActionToken.domain.enumerated.UserActionTokenDeleteCauseEnum;
import draczek.facadetemplate.userActionToken.domain.enumerated.UserActionTokenEnum;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;

/**
 * Class for activation of new, inactive user.
 */
@RequiredArgsConstructor
class ActivationUseCase {

  private final UserActionTokenFacade userActionTokenFacade;
  private final UserFacade userFacade;

  /**
   * Method for activation of new, inactive user.
   *
   * @param key token key
   */
  public void activate(String key) {
    key = URLDecoder.decode(key, StandardCharsets.UTF_8);
    if (key.endsWith("=")) {
      key = key.substring(0, key.length() - 1);
    }
    UserActionToken token = userActionTokenFacade.get(key, UserActionTokenEnum.ACTIVATE_ACCOUNT);
    userFacade.activate(token.getUser());
    userActionTokenFacade.delete(token, UserActionTokenDeleteCauseEnum.USED);
  }
}
