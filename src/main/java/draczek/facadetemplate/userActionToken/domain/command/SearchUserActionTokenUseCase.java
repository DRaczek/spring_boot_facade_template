package draczek.facadetemplate.userActionToken.domain.command;

import draczek.facadetemplate.userActionToken.domain.enumerated.UserActionTokenEnum;
import lombok.RequiredArgsConstructor;

/**
 * Class for stuff related to searching UserActionToken entities.
 */
@RequiredArgsConstructor
class SearchUserActionTokenUseCase {
  private final UserActionTokenRepository userActionTokenRepository;
  private final UserActionTokenValidationHelper validationHelper;

  /**
   * Method for getting UserActionToken by its key and action.
   *
   * @param key token key
   * @param action token action
   * @return UserActionToken instance
   */
  public UserActionToken get(String key, UserActionTokenEnum action) {
    UserActionToken token = userActionTokenRepository.get(key);
    validationHelper.validate(token, action);
    return token;
  }
}
