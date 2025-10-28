package draczek.facadetemplate

import draczek.facadetemplate.auth.command.domain.AuthenticationFacade
import draczek.facadetemplate.auth.command.dto.LoginDto
import draczek.facadetemplate.common.enumerated.StatusEnum
import draczek.facadetemplate.user.domain.command.User
import draczek.facadetemplate.user.domain.command.UserRepository
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
@SuppressWarnings("GroovyAccessibility")
class BaseITSpecification extends Specification {

    protected static final String USERNAME = "draczekprojekt@gmail.com"
    protected static final String PASSWORD = "1234"

    @Autowired
    TestRestTemplate restTemplate
    @Autowired
    AuthenticationFacade authenticationFacade
    @Autowired
    UserRepository userRepository
    @Autowired
    AuthenticationManager authenticationManager

    String getInitPasswordLoginUserApp() {
        return PASSWORD
    }

    User getLoginUserApp() {
        return userRepository.get(USERNAME, StatusEnum.ACTIVE)
    }

    String getTokenLoginUserApp(String username, String password) {
        def dto = new LoginDto(username, password)
        def tokenUserAppDto = authenticationFacade.login(dto)
        return tokenUserAppDto.accessToken
    }

    HttpHeaders getFullAccessAuthHttpHeader() {
        return getFullAccessAuthHttpHeader(USERNAME, PASSWORD)
    }

    HttpHeaders getFullAccessAuthHttpHeader(String username, String password) {
        def bearerToken = getTokenLoginUserApp(username, password)
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON))
        headers.setBearerAuth(bearerToken)
        return headers
    }

    void setContextAuthentication() {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(USERNAME, PASSWORD))
        SecurityContextHolder.getContext().setAuthentication(authentication)
    }
}