package draczek.facadetemplate.auth.command.domain;

import java.util.List;
import java.util.stream.Collectors;
import draczek.facadetemplate.role.domain.command.Role;
import draczek.facadetemplate.role.domain.enumerated.RoleEnum;
import draczek.facadetemplate.user.domain.command.JwtUtils;
import draczek.facadetemplate.user.domain.command.RefreshToken;
import draczek.facadetemplate.user.domain.command.User;
import draczek.facadetemplate.user.domain.command.UserFacade;
import draczek.facadetemplate.user.domain.dto.JwtDto;
import draczek.facadetemplate.user.domain.dto.RefreshTokenDto;
import draczek.facadetemplate.user.domain.dto.UserTokenDto;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

/**
 * Use case for refreshing the user's token
 */
@RequiredArgsConstructor
class RefreshUserTokenUseCase {

  private final UserFacade userFacade;
  private final JwtUtils jwtUtils;

  /**
   * Refreshes the token authentication for the the user.
   *
   * @param dto RefreshTokenDto containing the refresh token.
   * @return UserTokenDto containing the refreshed access token and other user information.
   */
  public UserTokenDto refresh(@NotNull RefreshTokenDto dto) {
    RefreshToken token = userFacade.refreshRefreshToken(dto.getToken());
    User user = token.getUser();
    JwtDto jwt = jwtUtils.generateTokenFromUsername(user.getUsername());

    List<String> roles = user.getRoles().stream()
        .map(Role::getName)
        .map(RoleEnum::getValue)
        .collect(Collectors.toList());

    return new UserTokenDto(
            user.getUuid(),
            user.getUsername(),
            roles,
            jwt.getToken(),
            jwt.getExpiration(),
            token.getToken());
  }
}