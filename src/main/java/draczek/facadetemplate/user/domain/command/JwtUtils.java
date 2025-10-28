package draczek.facadetemplate.user.domain.command;


import draczek.facadetemplate.user.domain.dto.JwtDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utils for JWT tokens.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

  private final JwtProperties jwtProperties;

  /**
   * Method for token generation.
   *
   * @param username user name
   * @return dto JwtDto
   */
  public JwtDto generateTokenFromUsername(String username) {
    Date issuedAt = new Date();
    Date expiration = new Date(issuedAt.getTime() + jwtProperties.getExpirationMs());
    String token = Jwts.builder()
        .subject(username)
        .issuedAt(issuedAt)
        .expiration(expiration)
        .signWith(decodeSecret(), Jwts.SIG.HS512)
        .compact();
    return new JwtDto(token, expiration);
  }

  /**
   * Method for getting username from JWT token.
   *
   * @param token token JWT.
   * @return username
   */
  public String getUsernameFromJwtToken(String token) {
    return Jwts.parser()
        .verifyWith(decodeSecret())
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
  }

  /**
   * Method to verify JWT token.
   *
   * @param token token JWT.
   * @return indicates if token is valid
   */
  public boolean validateJwtToken(String token) {
    try {
      String username = getUsernameFromJwtToken(token);
      return username != null && !username.isEmpty();
    } catch (MalformedJwtException e) {
      log.error("Token: '{}' - Invalid JWT token: {}", token, e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("Token: '{}' - JWT token is expired: {}", token, e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("Token: '{}' - JWT token is unsupported: {}", token, e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("Token: '{}' - JWT claims string is empty: {}", token, e.getMessage());
    }
    return false;
  }

  /**
   * Method for decoding JWT's secret key.
   *
   * @return Decoded SecretKey instance.
   */
  private SecretKey decodeSecret() {
    byte[] bytes = Decoders.BASE64.decode(jwtProperties.getSecret());
    return Keys.hmacShaKeyFor(bytes);
  }
}