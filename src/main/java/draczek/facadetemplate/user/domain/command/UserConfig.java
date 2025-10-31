package draczek.facadetemplate.user.domain.command;

import draczek.facadetemplate.address.domain.command.AddressFacade;
import draczek.facadetemplate.common.FileStorageService;
import draczek.facadetemplate.common.FileUploadValidator;
import draczek.facadetemplate.infrastructure.security.domain.command.SecurityFacade;
import draczek.facadetemplate.role.domain.command.RoleFacade;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Config class for UserFacade.
 */
@Configuration
class UserConfig {

  @Bean
  UserFacade userFacade(
      UserRepository userRepository,
      SecurityFacade securityFacade,
      PasswordEncoder passwordEncoder,
      FileStorageService fileStorageService,
      RoleFacade roleFacade,
      AddressFacade addressFacade,
      RefreshTokenRepository refreshTokenRepository,
      RefreshTokenHistoryRepository refreshTokenHistoryRepository,
      JwtUtils jwtUtils) {
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    SearchUserUseCase searchUserUseCase = new SearchUserUseCase(userRepository,
        userMapper,
        securityFacade);

    CreateUserUseCase createUserUseCase = new CreateUserUseCase(
        userRepository,
        passwordEncoder,
        roleFacade);

    UserValidationHelper userValidationHelper = new UserValidationHelper(userRepository);

    FileUploadValidator fileUploadValidator = new FileUploadValidator();
    UpdateUserValidationHelper updateUserValidationHelper
        = new UpdateUserValidationHelper(fileUploadValidator);

    UpdateUserUseCase updateUserUseCase = new UpdateUserUseCase(
        userRepository,
        passwordEncoder,
        securityFacade,
        userMapper,
        updateUserValidationHelper,
        fileStorageService,
        addressFacade);

    DeleteRefreshTokenUseCase deleteRefreshTokenUseCase = new DeleteRefreshTokenUseCase(
        refreshTokenRepository,
        refreshTokenHistoryRepository);

    CreateRefreshTokenUseCase createRefreshTokenUseCase = new CreateRefreshTokenUseCase(
        deleteRefreshTokenUseCase,
        refreshTokenRepository,
        jwtUtils);

    SearchRefreshTokenUseCase searchRefreshTokenUseCase = new SearchRefreshTokenUseCase(
        refreshTokenRepository);

    RefreshRefreshTokenUseCase refreshRefreshTokenUseCase = new RefreshRefreshTokenUseCase(
        searchRefreshTokenUseCase,
        deleteRefreshTokenUseCase,
        jwtUtils,
        createRefreshTokenUseCase);

    LogoutUserUseCase logoutUserUseCase = new LogoutUserUseCase(
        refreshTokenRepository,
        deleteRefreshTokenUseCase);

    return new UserFacade(
        searchUserUseCase,
        createUserUseCase,
        userValidationHelper,
        updateUserUseCase,
        createRefreshTokenUseCase,
        refreshRefreshTokenUseCase,
        logoutUserUseCase);
  }
}
