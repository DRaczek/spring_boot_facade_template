package draczek.facadetemplate.configuration;

import static draczek.facadetemplate.user.domain.command.WebSecurityAuthTokenFilter.AUTHORIZATION;
import static draczek.facadetemplate.user.domain.command.WebSecurityAuthTokenFilter.BEARER;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger's config.
 */
@Configuration
class SwaggerConfiguration {

  /**
   * Bean of Swagger's OpenApi Config.
   *
   * @return OpenAPI instance
   */
  @Bean
  public OpenAPI securedOpenApi() {
    return new OpenAPI()
        .components(new Components().addSecuritySchemes(BEARER + "-jwt",
            new SecurityScheme().type(SecurityScheme.Type.APIKEY).scheme(BEARER)
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name(AUTHORIZATION)))
        .info(new Info().title("Spring Boot facade structure template"))
        .addSecurityItem(new SecurityRequirement().addList(BEARER + "-jwt",
            Arrays.asList("read", "write")));
  }
}