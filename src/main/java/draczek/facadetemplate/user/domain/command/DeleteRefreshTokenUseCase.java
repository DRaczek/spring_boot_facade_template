package draczek.facadetemplate.user.domain.command;

import java.util.List;
import java.util.stream.Collectors;
import draczek.facadetemplate.common.enumerated.StatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

/**
 * Use case containing logic behind deleting user's refresh token
 */
@RequiredArgsConstructor
class DeleteRefreshTokenUseCase {

  private final RefreshTokenRepository refreshTokenRepository;
  private final RefreshTokenHistoryRepository refreshTokenHistoryRepository;

  /**
   * Moves the tokens associated with the user to history table and soft deletes them.
   *
   * @param user The user whose tokens should be moved to history and soft deleted.
   */
  public void deleteAllByUser(@NotNull User user) {
    List<RefreshToken> tokens = refreshTokenRepository.findByUser(user);
    List<RefreshTokenHistory> tokenHistories = tokens.stream()
        .map(t -> this.map(t, user))
        .collect(Collectors.toList());
    refreshTokenHistoryRepository.saveAllAndFlush(tokenHistories);
    refreshTokenRepository.deleteAllInBatch(tokens);
  }

  private RefreshTokenHistory map(RefreshToken refreshToken, User user) {
    RefreshTokenHistory history = RefreshTokenHistory.builder()
        .token(refreshToken.getToken())
        .expirationDate(refreshToken.getExpirationDate())
        .user(refreshToken.getUser())
        .build();
    history.setCreatedDate(refreshToken.getCreatedDate());
    history.setUserIdCreated(refreshToken.getUserIdCreated());
    history.setUserIdLastModified(user.getId());
    history.setStatus(StatusEnum.DELETE);
    return history;
  }
}