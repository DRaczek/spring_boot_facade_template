package draczek.facadetemplate.infrastructure.security.domain.command;

import draczek.facadetemplate.user.domain.command.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Class to ease fetching data from security context.
 */
@RequiredArgsConstructor
class SecurityContextHandler {

  /**
   * Method returning data about logged in user.
   */
  public UserDetailsImpl getLoggedInUser() {
    return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}