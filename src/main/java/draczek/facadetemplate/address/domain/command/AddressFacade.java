package draczek.facadetemplate.address.domain.command;

import draczek.facadetemplate.address.domain.dto.UpdateAddressDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

/**
 * Address's package facade.
 */
@Validated
@RequiredArgsConstructor
@Transactional
public class AddressFacade {
  private final UpdateAddressUseCase updateAddressUseCase;

  /**
   * Method for updating logged in user's address.
   *
   * @param dto  UpdateAddressDto
   * @return modified AddressDto
   */
  public Address update(UpdateAddressDto dto) {
    return updateAddressUseCase.updateDto(dto);
  }

  /**
   * Method for updating address.
   *
   * @param dto  UpdateAddressDto
   * @return modified AddressDto
   */
  public Address update(UpdateAddressDto dto, UUID uuid) {
    return updateAddressUseCase.updateDto(dto, uuid);
  }
}
