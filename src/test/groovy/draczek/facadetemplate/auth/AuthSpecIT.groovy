package draczek.facadetemplate.auth

import draczek.facadetemplate.BaseCleanupSpecification
import draczek.facadetemplate.common.enumerated.StatusEnum
import draczek.facadetemplate.common.exception.DtoInvalidException
import draczek.facadetemplate.user.domain.dto.UserTokenDto
import draczek.facadetemplate.userActionToken.domain.command.UserActionToken
import draczek.facadetemplate.userActionToken.domain.enumerated.UserActionTokenEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@SuppressWarnings("GroovyAccessibility")
class AuthSpecIT extends BaseCleanupSpecification {

    private static final String BASE_URL = "/api/auth"
    private static final String RESET_PASSWORD_URL = "$BASE_URL/reset-password"
    private static final String REGISTRATION_URL = "$BASE_URL/register"
    private static final String LOGIN_URL = "$BASE_URL/login"
    private static final String ACTIVATION_URL = "$BASE_URL/activate"
    private static final String RESET_PASSWORD_STEP_1_URL = "$RESET_PASSWORD_URL/step-1"
    private static final String RESET_PASSWORD_STEP_2_URL = "$RESET_PASSWORD_URL/step-2"

    @Autowired
    AuthHelper authHelper

    def "should throws exception when incorrect required fields"() {
        given:
        def dto = authHelper.buildRegistrationClientDto(
                username: username,
                password : password,
                confirmPassword : confirmPassword
        )
        when:
        ResponseEntity<DtoInvalidException> response = restTemplate.postForEntity(
                REGISTRATION_URL,
                dto,
                DtoInvalidException.class)
        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        where:
        username           | password   | confirmPassword
        null               | "1!Test2@" | "1!Test2@"
        ""                 | "1!Test2@" | "1!Test2@"
    }

    def "should register a new inactive user"() {
        given:
        def username = "test1@test.com"
        def dto = authHelper.buildRegistrationClientDto(
                username : username
        )
        when:
        def response = restTemplate.postForEntity(
                REGISTRATION_URL,
                dto,
                Void.class)
        then:
        response.statusCode == HttpStatus.CREATED
        authHelper.checkUserStatus(username, StatusEnum.INACTIVE)
    }

    def "should activate new user"() {
        given:
        def username = "test@test.com"
        def registrationDto = authHelper.buildRegistrationClientDto(
                username : username
        )
        authHelper.registerInactiveUser(registrationDto)
        UserActionToken token = authHelper.fetchUserActionToken(username, UserActionTokenEnum.ACTIVATE_ACCOUNT)
        authHelper.updateUserActionToken(token, "correct token key")
        and:
        authHelper.createUserActionToken(token.user, UserActionTokenEnum.RESET_PASSWORD,
                "incorrect token key")
        when:
        def response = restTemplate.postForEntity(
                ACTIVATION_URL,
                activeAccountTokenKey,
                Void.class)
        then:
        response.statusCode == code
        authHelper.checkUserStatus(username, status)
        where:
        activeAccountTokenKey | code                 | status
        "correct token key"   | HttpStatus.OK        | StatusEnum.ACTIVE
        "incorrect token key" | HttpStatus.NOT_FOUND | StatusEnum.INACTIVE
    }

    def "should do step 1 password reset and create token"() {
        given:
        def user = getLoggedInUser()
        when:
        def response = restTemplate.postForEntity(
                RESET_PASSWORD_STEP_1_URL,
                user.username,
                Void.class
        )
        then:
        response.statusCode == HttpStatus.OK
        authHelper.getUserActionTokens(user, UserActionTokenEnum.RESET_PASSWORD).size() == 1
    }

    def "should do step 2 password reset and delete token"() {
        given:
        def user = getLoggedInUser()
        and:
        authHelper.createUserActionToken(user,
                UserActionTokenEnum.RESET_PASSWORD, "token key")
        and:
        def dto = authHelper.buildResetPasswordStepTwoDto(
                key: tokenKey,
                password: password,
                confirmPassword: confirmPassword
        )
        when:
        def response = restTemplate.postForEntity(
                RESET_PASSWORD_STEP_2_URL,
                dto,
                DtoInvalidException.class)
        then:
        response.statusCode == code
        authHelper.getUserActionTokens(user, UserActionTokenEnum.RESET_PASSWORD).size() == tokenListSize
        cleanup:
        authHelper.changePassword(getLoggedInUser(), getInitPassword())
        where:
        tokenKey          | password   | confirmPassword | code                   | tokenListSize
        "token key"       | "ZAQ!2wsx" | "ZAQ!2wsx"      | HttpStatus.OK          | 0
        "token key"       | "ZAQ!2wsx" | "ZAQ!2wsxx"     | HttpStatus.BAD_REQUEST | 1
        "token key"       | "aaa"      | "aaa"           | HttpStatus.BAD_REQUEST | 1
        "incorrect token" | "ZAQ!2wsx" | "ZAQ!2wsx"      | HttpStatus.NOT_FOUND   | 1
    }

    def "should login an user"() {
        given:
        def dto = authHelper.buildLoginDto(
                username: USERNAME,
                password: PASSWORD
        )
        when:
        def response = restTemplate.postForEntity(
                LOGIN_URL,
                dto,
                UserTokenDto.class
        )
        then:
        response.statusCode == HttpStatus.OK
        response.body.token.length() > 0
    }
}