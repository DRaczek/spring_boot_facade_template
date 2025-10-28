package draczek.facadetemplate.common.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Entity's status enum.
 */
@RequiredArgsConstructor
@Getter
public enum StatusEnum {
  DELETE((short) 0),
  ACTIVE((short) 1),
  INACTIVE((short) 2),
  BANNED((short) 3);

  private final short value;
}