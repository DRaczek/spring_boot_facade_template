package draczek.facadetemplate.auth.command.domain;

import draczek.facadetemplate.auth.command.dto.LoginDto;
import draczek.facadetemplate.user.domain.command.JwtUtils;
import draczek.facadetemplate.user.domain.command.UserDetailsImpl;
import draczek.facadetemplate.user.domain.command.UserFacade;
import draczek.facadetemplate.user.domain.dto.JwtDto;
import draczek.facadetemplate.user.domain.dto.UserTokenDto;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Class for logging in the user.
 */
@RequiredArgsConstructor
class LoginUseCase {
  private final JwtUtils jwtUtils;
  private final AuthenticationManager authenticationManager;
  private final UserFacade userFacade;

  /**
   * Method for logging in.
   */
  public UserTokenDto login(@NotNull LoginDto dto) {
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(),
            dto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    JwtDto jwt = jwtUtils.generateTokenFromUsername(userDetails.getUsername());
    List<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
    return new UserTokenDto(
        userDetails.getUuid(),
        userDetails.getUsername(),
        roles,
        jwt.getToken(),
        jwt.getExpiration(),
        userFacade.createRefreshToken(userDetails.getUserDetails().getAccount().getUser()).getToken());
  }
}
