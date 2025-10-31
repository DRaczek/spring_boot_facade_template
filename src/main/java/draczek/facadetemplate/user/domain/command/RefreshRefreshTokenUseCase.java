package draczek.facadetemplate.user.domain.command;

import java.time.LocalDateTime;
import draczek.facadetemplate.user.domain.exception.RefreshTokenNotFoundException;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case for refreshing the refresh token.
 */
@Slf4j
@RequiredArgsConstructor
class RefreshRefreshTokenUseCase {

  private final SearchRefreshTokenUseCase searchRefreshTokenUseCase;
  private final DeleteRefreshTokenUseCase deleteRefreshTokenUseCase;
  private final JwtUtils jwtUtils;
  private final CreateRefreshTokenUseCase createRefreshTokenUseCase;

  /**
   * Refreshes the user's token based on the provided token.
   *
   * @param token The refresh token used to refresh the RefreshToken.
   * @return The refreshed RefreshToken.
   * @throws RefreshTokenNotFoundException If the refresh token is not found in the database.
   */
  public RefreshToken refresh(@NotBlank String token) throws RefreshTokenNotFoundException {
    RefreshToken refreshToken = searchRefreshTokenUseCase.getEntity(token);
    if (isTokenHasExpired(refreshToken)) {
      deleteRefreshTokenUseCase.deleteAllByUser(refreshToken.getUser());
      throw new RefreshTokenNotFoundException(token);
    }
    if (isTokenExpires(refreshToken)) {
      refreshToken = createRefreshTokenUseCase.create(refreshToken.getUser());
    }
    return refreshToken;
  }

  private boolean isTokenHasExpired(RefreshToken refreshToken) {
    return refreshToken.getExpirationDate().isBefore(LocalDateTime.now());
  }

  private boolean isTokenExpires(RefreshToken refreshToken) {
      long nanos = jwtUtils.getJwtProperties().getExpirationMs() * 1000000;
    return refreshToken.getExpirationDate().isBefore(LocalDateTime.now().plusNanos(nanos));
  }
}