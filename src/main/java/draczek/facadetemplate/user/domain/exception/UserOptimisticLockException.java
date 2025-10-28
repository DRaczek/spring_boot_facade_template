package draczek.facadetemplate.user.domain.exception;

import draczek.facadetemplate.common.exception.OptimisticLockException;

/**
 * Implementation of User's invalid version exception.
 */
public class UserOptimisticLockException extends OptimisticLockException {
  private static final String DEFAULT_CODE = "user_app_optimistic_lock";

  public UserOptimisticLockException() {
    super("Version of User is invalid", DEFAULT_CODE);
  }
}



