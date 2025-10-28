package draczek.facadetemplate.address.domain.command;

import draczek.facadetemplate.address.domain.dto.UpdateAddressDto;
import draczek.facadetemplate.common.enumerated.StatusEnum;
import draczek.facadetemplate.infrastructure.security.domain.command.SecurityFacade;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

/**
 * UseCase for updating addresses.
 */
@RequiredArgsConstructor
public class UpdateAddressUseCase {
  private final AddressRepository addressRepository;
  private final SecurityFacade securityFacade;

  /**
   * Method for updating Addresses.
   *
   * @param dto UpdateAddressDto
   * @return modified AddressDto
   */
  public Address updateDto(UpdateAddressDto dto) {
    return update(dto,null);
  }

  /**
   * Method for updating Addresses.
   *
   * @param dto UpdateAddressDto
   * @return modified AddressDto
   */
  public Address updateDto(UpdateAddressDto dto, UUID uuid) {
    return update(dto, uuid);
  }

  private Address update(UpdateAddressDto dto, UUID uuid) {
    Address address;
    if(uuid == null) {
      address = securityFacade.getLoggedInAccount().getAddress();
    } else{
      address = addressRepository.get(uuid);
    }
    if (address == null) {
      address = Address.builder()
          .uuid(UUID.randomUUID())
          .city(dto.getCity())
          .postalCode(dto.getPostalCode())
          .postalName(dto.getPostalName())
          .street(dto.getStreet())
          .streetNumber(dto.getStreetNumber())
          .apartmentNumber(dto.getApartmentNumber())
          .country(dto.getCountry())
          .build();
      address.setStatus(StatusEnum.ACTIVE);
      address.setUserIdCreated(securityFacade.getLoggedInUser().getId());
      address.setUserIdLastModified(securityFacade.getLoggedInUser().getId());
    } else {
      address.setCity(dto.getCity());
      address.setPostalCode(dto.getPostalCode());
      address.setPostalName(dto.getPostalName());
      address.setStreet(dto.getStreet());
      address.setStreetNumber(dto.getStreetNumber());
      address.setApartmentNumber(dto.getApartmentNumber());
      address.setCountry(dto.getCountry());
      address.setVersion(dto.getVersion());
      address.setUserIdLastModified(securityFacade.getLoggedInUser().getId());
    }
    return addressRepository.saveAndFlush(address);
  }
}
