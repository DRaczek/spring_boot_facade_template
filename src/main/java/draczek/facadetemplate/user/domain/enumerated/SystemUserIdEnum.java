package draczek.facadetemplate.user.domain.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * System users ids enum.
 */
@RequiredArgsConstructor
@Getter
public enum SystemUserIdEnum {
  SYSTEM(1L);

  private final Long id;
}
