package draczek.facadetemplate.address.domain.exception;

import draczek.facadetemplate.common.exception.EntityNotFoundException;
import java.util.UUID;

/**
 * AddressNotFoundException entity not found exception.
 */
public final class AddressNotFoundException extends EntityNotFoundException {

  private static final String DEFAULT_CODE = "address_not_found";

  public AddressNotFoundException(UUID uuid) {
    super(String.format("Could not find Address with UUID: %s", uuid), DEFAULT_CODE);
  }
}

