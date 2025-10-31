package draczek.facadetemplate.user.domain.command;

import java.util.Optional;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case for logging out by deleting refresh token
 */
@Slf4j
@RequiredArgsConstructor
class LogoutUserUseCase {

  private final RefreshTokenRepository refreshTokenRepository;
  private final DeleteRefreshTokenUseCase deleteRefreshTokenUseCase;

  /**
   * Logs out a user by deleting the associated refresh token.
   *
   * @param refreshToken the refresh token of the user
   */
  public void logout(@NotBlank String refreshToken) {
    Optional<RefreshToken> token = refreshTokenRepository.findByToken(refreshToken);
    token.ifPresent(refreshTokenEntity -> {
      deleteRefreshTokenUseCase.deleteAllByUser(refreshTokenEntity.getUser());
    });
  }
}