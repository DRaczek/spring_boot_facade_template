package draczek.facadetemplate.userActionToken.domain.command;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * UserActionToken Facade's config class.
 */
@Configuration
class UserActionTokenConfig {
  @Bean
  UserActionTokenFacade userActionTokenFacade(
      PasswordEncoder passwordEncoder,
      UserActionTokenRepository userActionTokenRepository,
      UserActionTokenHistoryRepository userActionTokenHistoryRepository) {

    CreateUserActionTokenUseCase createUserActionTokenUseCase = new CreateUserActionTokenUseCase(
        passwordEncoder,
        userActionTokenRepository);

    UserActionTokenValidationHelper userActionTokenValidationHelper
        = new UserActionTokenValidationHelper();
    SearchUserActionTokenUseCase searchUserActionTokenUseCase = new SearchUserActionTokenUseCase(
        userActionTokenRepository,
        userActionTokenValidationHelper);

    DeleteUserActionTokenUseCase deleteUserActionTokenUseCase = new DeleteUserActionTokenUseCase(
        userActionTokenRepository,
        userActionTokenHistoryRepository);

    return new UserActionTokenFacade(
        createUserActionTokenUseCase,
        searchUserActionTokenUseCase,
        deleteUserActionTokenUseCase);
  }
}
