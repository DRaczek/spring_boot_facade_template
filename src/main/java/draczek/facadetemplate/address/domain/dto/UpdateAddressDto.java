package draczek.facadetemplate.address.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

/**
 * Update address dto.
 */
@Value
public class UpdateAddressDto {
  String city;

  String postalName;

  String postalCode;

  String street;

  String streetNumber;

  String apartmentNumber;

  String country;

  @NotNull
  Integer version;
}
