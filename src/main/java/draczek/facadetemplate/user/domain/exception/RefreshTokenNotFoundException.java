package draczek.facadetemplate.user.domain.exception;

import draczek.facadetemplate.common.exception.EntityNotFoundException;

import static java.lang.String.format;

/**
 * Could not find RefreshToken exception.
 */
public class RefreshTokenNotFoundException extends EntityNotFoundException {

  private static final String DEFAULT_CODE = "refresh_token_not_found";

  public RefreshTokenNotFoundException(String token) {
    super(format("Could not find RefreshToken with value: %s", token), DEFAULT_CODE);
  }

}