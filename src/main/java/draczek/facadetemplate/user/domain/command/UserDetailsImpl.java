package draczek.facadetemplate.user.domain.command;

import java.util.Collection;
import java.util.UUID;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Custom UserDetails implementation.
 */
@Value
public class UserDetailsImpl implements UserDetails {

  Long id;

  UUID uuid;

  String username;

  String password;

  boolean enabled;

  Collection<? extends GrantedAuthority> authorities;

  boolean accountNonExpired;

  boolean accountNonLocked;

  boolean credentialsNonExpired;

  User userDetails;
}
