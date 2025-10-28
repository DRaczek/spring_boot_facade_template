package draczek.facadetemplate.user.domain.command;

import draczek.facadetemplate.common.enumerated.StatusEnum;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Class implementing user details fetching.
 */

@Service("userDetailsService")
class SearchUserDetailsUseCase implements UserDetailsService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Autowired
  public SearchUserDetailsUseCase(UserRepository userRepository) {
    this.userMapper = Mappers.getMapper(UserMapper.class);
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    return userMapper.toUserDetails(userRepository.get(username, StatusEnum.ACTIVE));
  }
}