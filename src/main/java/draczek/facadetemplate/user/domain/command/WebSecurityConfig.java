package draczek.facadetemplate.user.domain.command;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Security configuration class.
 */
@Configuration
@EnableMethodSecurity()
@RequiredArgsConstructor
class WebSecurityConfig {

  private static final String[] AUTH_WHITELIST = {
      "/upload/**",
      "/api/auth/login",
      "/api/auth/register",
      "/api/auth/activate",
      "/api/auth/reset-password/step-1",
      "/api/auth/reset-password/step-2",
      "/api/auth/logout",
      "/api/auth/refresh-token",
      "/swagger-resources/**",
      "/swagger-ui*",
      "/swagger-ui/**",
      "/v3/api-docs*",
      "/v3/api-docs/**",
      "/webjars/**"
  };

  private static final String[] GET_AUTH_WHITELIST = {
//      "/api/companies/**",
//      "/api/advertisements/**",
  };

  private final WebSecurityAuthEntryPointJwt unauthorizedHandler;

  @Bean
  public WebSecurityAuthTokenFilter authenticationJwtTokenFilter() {
    return new WebSecurityAuthTokenFilter();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
      throws Exception {
    return authConfig.getAuthenticationManager();
  }

  /**
   * Method, that returns security filter chain.
   *
   * @param http HttpSecurity object
   * @return SecurityFilterChain instance
   * @throws Exception cors exception
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .cors(cors -> cors.configure(http))
      .csrf(csrf -> csrf.disable())
      .exceptionHandling(exception -> exception
              .authenticationEntryPoint(unauthorizedHandler)
      )
      .sessionManagement(session -> session
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .authorizeHttpRequests(auth -> auth
              .requestMatchers(AUTH_WHITELIST).permitAll()
              .requestMatchers(HttpMethod.GET, GET_AUTH_WHITELIST).permitAll()
              .anyRequest().authenticated()
      )
      .addFilterBefore(authenticationJwtTokenFilter(),
              UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  /**
   * Method, that creates a cors filter, based on provided configuration.
   *
   * @return FilterRegistrationBean instance
   */
  @Bean
  public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
    CorsConfiguration config = new CorsConfiguration();
    //TODO change allowed origins
    config.setAllowCredentials(true);
    config.setAllowedOriginPatterns(Collections.singletonList("*"));
    config.setAllowedMethods(Collections.singletonList("*"));
    config.setAllowedHeaders(Collections.singletonList("*"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return bean;
  }
}