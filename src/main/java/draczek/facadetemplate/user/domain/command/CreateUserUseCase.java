package draczek.facadetemplate.user.domain.command;

import draczek.facadetemplate.address.domain.command.Address;
import draczek.facadetemplate.auth.command.dto.RegistrationDto;
import draczek.facadetemplate.common.enumerated.StatusEnum;
import draczek.facadetemplate.role.domain.command.RoleFacade;
import draczek.facadetemplate.role.domain.enumerated.RoleEnum;
import draczek.facadetemplate.user.domain.enumerated.SystemUserIdEnum;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Class related to creating Users.
 */
@RequiredArgsConstructor
class CreateUserUseCase {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RoleFacade roleFacade;

  public User create(RegistrationDto dto) {
      User user = User.builder()
        .uuid(UUID.randomUUID())
        .enabled(false)
        .username(dto.getUsername())
        .password(passwordEncoder.encode(dto.getPassword()))
        .account(Account.builder()
            .uuid(UUID.randomUUID())
            .address(Address.builder()
                .uuid(UUID.randomUUID())
                .build())
            .build())
        .roles(Set.of(roleFacade.getEntity(RoleEnum.ROLE_USER)))
        .accountNonExpired(true)
        .accountNonLocked(true)
        .credentialsNonExpired(true)
        .build();
    user.getAccount().setUser(user);
    user.getAccount().setStatus(StatusEnum.INACTIVE);
    user.getAccount().getAddress().setStatus(StatusEnum.INACTIVE);
    user.setStatus(StatusEnum.INACTIVE);
    user.setUserIdCreated(SystemUserIdEnum.SYSTEM.getId());
    user.setUserIdLastModified(SystemUserIdEnum.SYSTEM.getId());
    return userRepository.saveAndFlush(user);
  }
}
