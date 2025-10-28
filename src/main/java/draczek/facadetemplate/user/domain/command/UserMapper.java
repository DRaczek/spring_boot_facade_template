package draczek.facadetemplate.user.domain.command;

import draczek.facadetemplate.user.domain.dto.UserDto;
import java.util.Collection;
import java.util.stream.Collectors;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Mapper User.
 */
@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

  @Mappings({
      @Mapping(target = "authorities", source = ".", qualifiedByName = "getUserRoles"),
      @Mapping(target = "userDetails", source = "user")
  })
  UserDetailsImpl toUserDetails(User user);

  @Mappings({
  })
  UserDto toDto(User user);

  /**
   * Method for roles mapping.
   */
  @Named("getUserRoles")
  default Collection<? extends GrantedAuthority> getUserRoles(User user) {
    return user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());
  }
}