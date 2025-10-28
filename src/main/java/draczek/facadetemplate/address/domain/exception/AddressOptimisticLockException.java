package draczek.facadetemplate.address.domain.exception;

import draczek.facadetemplate.common.exception.OptimisticLockException;

/**
 * Implementation of Address's invalid version exception.
 */
public class AddressOptimisticLockException extends OptimisticLockException {
  private static final String DEFAULT_CODE = "address_optimistic_lock";

  public AddressOptimisticLockException() {
    super("Version of Address is invalid", DEFAULT_CODE);
  }
}
