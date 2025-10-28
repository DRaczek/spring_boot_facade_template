package draczek.facadetemplate.user.domain.command;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT token settings.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Component
@ConfigurationProperties(prefix = "jwt")
class JwtProperties {

  private Long expirationMs;
  private String secret;
}