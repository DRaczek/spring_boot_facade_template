package draczek.facadetemplate.infrastructure.security.domain.command;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Security config.
 */
@Configuration
class SecurityConfig {
  @Bean
  SecurityFacade securityFacade() {

    SecurityContextHandler securityContextHandler = new SecurityContextHandler();
    PasswordValidationHelper passwordValidationHelper = new PasswordValidationHelper();

    return new SecurityFacade(
        securityContextHandler,
        passwordValidationHelper);
  }
}