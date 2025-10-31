package draczek.facadetemplate.user.domain.command;

import draczek.facadetemplate.common.enumerated.StatusEnum;
import draczek.facadetemplate.user.domain.dto.JwtDto;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

/**
 * Use case containing logic behind refreshing user's token
 */
@RequiredArgsConstructor
class CreateRefreshTokenUseCase {

  private final DeleteRefreshTokenUseCase deleteRefreshTokenUseCase;
  private final RefreshTokenRepository refreshTokenRepository;
  private final JwtUtils jwtUtils;

  /**
   * Method implementing the process of refreshing user's token
   *
   * @return refresh token
   */
  public RefreshToken create(@NotNull User user) {
    deleteRefreshTokenUseCase.deleteAllByUser(user);
    JwtDto refreshJwt = jwtUtils.generateRefreshTokenFromUsername(user.getUsername());
    RefreshToken refreshToken = prepareRefreshToken(refreshJwt, user);
    refreshToken = refreshTokenRepository.save(refreshToken);
    return refreshToken;
  }

  private RefreshToken prepareRefreshToken(JwtDto refreshJwt, User user) {
    RefreshToken token = RefreshToken.builder()
        .token(refreshJwt.getToken())
        .expirationDate(refreshJwt.getExpiration())
        .user(user)
        .build();
    token.setStatus(StatusEnum.ACTIVE);
    token.setUserIdCreated(user.getId());
    token.setUserIdLastModified(user.getId());
    return token;
  }
}