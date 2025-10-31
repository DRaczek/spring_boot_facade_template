package draczek.facadetemplate.user.domain.command;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case for searching RefreshToken
 */
@Slf4j
@RequiredArgsConstructor
class SearchRefreshTokenUseCase {

  private final RefreshTokenRepository refreshTokenRepository;

  /**
   * Method for retrieving RefreshToken
   *
   * @param token refresh token
   * @return RefreshToken entity
   */
  public RefreshToken getEntity(@NotBlank String token) {
    return refreshTokenRepository.get(token);
  }
}