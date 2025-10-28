package draczek.facadetemplate.userActionToken.domain.command;

import draczek.facadetemplate.user.domain.command.User;
import draczek.facadetemplate.userActionToken.domain.enumerated.UserActionTokenDeleteCauseEnum;
import draczek.facadetemplate.userActionToken.domain.enumerated.UserActionTokenEnum;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * UserActionToken package's facade.
 */
@Validated
@RequiredArgsConstructor
@Transactional
public class UserActionTokenFacade {
  private final CreateUserActionTokenUseCase createUserActionTokenUseCase;
  private final SearchUserActionTokenUseCase searchUserActionTokenUseCase;
  private final DeleteUserActionTokenUseCase deleteUserActionTokenUseCase;

  /**
   * Service for creating user action token with provided action.
   *
   * @param user                Associated user
   * @param userActionTokenEnum action
   * @return created UserActionToken instance
   */
  public UserActionToken create(@NotNull User user,
                                @NotNull UserActionTokenEnum userActionTokenEnum) {
    return createUserActionTokenUseCase.create(user, userActionTokenEnum);
  }

  /**
   * Helper service for validating.
   *
   * @param key    token key
   * @param action token action
   * @return found UserActionToken instance
   */
  public UserActionToken get(@NotBlank String key, @NotNull UserActionTokenEnum action) {
    return searchUserActionTokenUseCase.get(key, action);
  }

  /**
   * Method for deleting UserActionToken and moving it to the history table.
   *
   * @param token UserActionToken instance
   * @param cause deletion cause
   */
  public void delete(@NotNull UserActionToken token,
                     @NotNull UserActionTokenDeleteCauseEnum cause) {
    deleteUserActionTokenUseCase.delete(token, cause);
  }
}
