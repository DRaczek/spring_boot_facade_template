package draczek.facadetemplate.auth

import draczek.facadetemplate.auth.command.domain.AuthenticationFacade
import draczek.facadetemplate.auth.command.dto.LoginDto
import draczek.facadetemplate.auth.command.dto.RegistrationDto
import draczek.facadetemplate.auth.command.dto.ResetPasswordStepTwoDto
import draczek.facadetemplate.common.enumerated.StatusEnum
import draczek.facadetemplate.user.domain.command.RefreshToken
import draczek.facadetemplate.user.domain.command.RefreshTokenHistoryRepository
import draczek.facadetemplate.user.domain.command.RefreshTokenRepository
import draczek.facadetemplate.user.domain.command.User
import draczek.facadetemplate.user.domain.command.UserFacade
import draczek.facadetemplate.user.domain.command.UserRepository
import draczek.facadetemplate.user.domain.dto.RefreshTokenDto
import draczek.facadetemplate.userActionToken.domain.command.UserActionToken
import draczek.facadetemplate.userActionToken.domain.command.UserActionTokenRepository
import draczek.facadetemplate.userActionToken.domain.enumerated.UserActionTokenEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.time.LocalDateTime
import java.util.stream.Collectors

@Component
@SuppressWarnings("GroovyAccessibility")
class AuthHelper {

    @Autowired
    UserRepository userRepository
    @Autowired
    AuthenticationFacade authenticationFacade
    @Autowired
    UserActionTokenRepository userActionTokenRepository
    @Autowired
    UserFacade userFacade
    @Autowired
    RefreshTokenRepository refreshTokenRepository
    @Autowired
    RefreshTokenHistoryRepository refreshTokenHistoryRepository

    boolean checkUserStatus(String username, StatusEnum expectedStatus) {
        User user = userRepository.findOneByUsername(username).orElseThrow()
        return user.status == expectedStatus
    }

    UserActionToken fetchUserActionToken(String username, UserActionTokenEnum type) {
        return userActionTokenRepository.findAll()
                .stream()
                .filter { token -> token.user.username == username && token.action == type }
                .collect(Collectors.toList())
                .first()
    }


    static RegistrationDto buildRegistrationClientDto(Map params = [:]) {
        new RegistrationDto(
                params.getOrDefault("username", "test@test.com") as String,
                params.getOrDefault("password", "Admin1234!") as String,
                params.getOrDefault("confirmPassword", "Admin1234!") as String,
        )
    }

    void changePassword(User user, String password) {
        userFacade.changePassword(user, password)
    }

    static LoginDto buildLoginDto(Map params = [:]) {
        new LoginDto(
                params.getOrDefault("username", null) as String,
                params.getOrDefault("password", null) as String
        )
    }

    static ResetPasswordStepTwoDto buildResetPasswordStepTwoDto(Map params = [:]) {
        new ResetPasswordStepTwoDto(
                params.getOrDefault("key", null) as String,
                params.getOrDefault("password", null) as String,
                params.getOrDefault("confirmPassword", null) as String,
        )
    }

    List<UserActionToken> getUserActionTokens(User user, UserActionTokenEnum type) {
        return userActionTokenRepository.findAll()
                .stream()
                .filter(token -> token.user.uuid == user.uuid && token.action == type)
                .collect(Collectors.toList())
    }

    UserActionToken createUserActionToken(User user, UserActionTokenEnum type, String tokenKey) {
        return userActionTokenRepository.saveAndFlush(
                UserActionToken.builder()
                        .user(user)
                        .key(tokenKey)
                        .createdDate(LocalDateTime.now())
                        .action(type)
                        .build()
        )
    }

    UserActionToken updateUserActionToken(UserActionToken userActionToken, String tokenKey) {
        userActionToken.key = tokenKey
        return userActionTokenRepository.saveAndFlush(userActionToken)
    }

    void registerInactiveUser(RegistrationDto dto) {
        authenticationFacade.register(dto)
    }

    RefreshToken createRefreshToken(User user, String token, LocalDateTime expirationDate) {
        RefreshToken refreshToken =   RefreshToken.builder()
                .user(user)
                .token(token)
                .expirationDate(expirationDate)
                .build()
        refreshToken.setStatus(StatusEnum.ACTIVE)
        return refreshTokenRepository.saveAndFlush(refreshToken)
    }

    RefreshTokenDto buildRefreshTokenDto(Map params = [:]){
        new RefreshTokenDto(
                params.getOrDefault("token", "token") as String
        )
    }

    boolean checkIfTheRefreshTokenGotSafeDeleted() {
        assert refreshTokenRepository.findAll().size() == 0
        assert refreshTokenHistoryRepository.findAll().size() == 1
        true
    }

}