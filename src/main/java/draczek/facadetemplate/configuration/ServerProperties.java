package draczek.facadetemplate.configuration;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Config class to read server properties.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Component
@ConfigurationProperties(prefix = "server")
public class ServerProperties {

  private String port;
  private String host;
  private Boolean debug;
}