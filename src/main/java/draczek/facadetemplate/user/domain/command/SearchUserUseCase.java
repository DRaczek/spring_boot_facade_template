package draczek.facadetemplate.user.domain.command;

import draczek.facadetemplate.common.enumerated.StatusEnum;
import draczek.facadetemplate.infrastructure.security.domain.command.SecurityFacade;
import draczek.facadetemplate.user.domain.dto.UserDto;
import lombok.RequiredArgsConstructor;

/**
 * Class for searching User's related data.
 */
@RequiredArgsConstructor
class SearchUserUseCase {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final SecurityFacade securityFacade;

  /**
   * Method, that returns data about logged in user.
   *
   * @return UserDto
   */
  public UserDto info() {
    return userMapper.toDto(securityFacade.getLoggedInUser().getUserDetails());
  }

  /**
   * Method for getting User instance by its email and status.
   *
   * @param email  user's email
   * @param status status
   * @return User instance
   */
  public User get(String email, StatusEnum status) {
    return userRepository.get(email, status);
  }
}
