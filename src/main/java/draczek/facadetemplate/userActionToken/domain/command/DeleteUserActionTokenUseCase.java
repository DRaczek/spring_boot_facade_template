package draczek.facadetemplate.userActionToken.domain.command;

import draczek.facadetemplate.userActionToken.domain.enumerated.UserActionTokenDeleteCauseEnum;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

/**
 * Class for stuff related to deleting UserActionToken entities.
 */
@RequiredArgsConstructor
class DeleteUserActionTokenUseCase {
  private final UserActionTokenRepository userActionTokenRepository;
  private final UserActionTokenHistoryRepository userActionTokenHistoryRepository;

  /**
   * Method for deleting UserActionToken and moving it to the history table.
   *
   * @param userActionToken UserActionToken instance
   * @param cause           deletion cause
   */
  public void delete(UserActionToken userActionToken,
                     UserActionTokenDeleteCauseEnum cause) {
    createUserActionTokenHistory(userActionToken, cause);
    userActionTokenRepository.delete(userActionToken);
  }

  private void createUserActionTokenHistory(UserActionToken userActionToken,
                                            UserActionTokenDeleteCauseEnum cause) {
    UserActionTokenHistory userActionTokenHistory = UserActionTokenHistory.builder()
        .user(userActionToken.getUser())
        .key(userActionToken.getKey())
        .action(userActionToken.getAction())
        .createdDate(userActionToken.getCreatedDate())
        .usedDate((cause == UserActionTokenDeleteCauseEnum.USED) ? LocalDateTime.now() : null)
        .deletedDate(LocalDateTime.now()).build();
    userActionTokenHistoryRepository.saveAndFlush(userActionTokenHistory);
  }
}
