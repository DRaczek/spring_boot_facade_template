package draczek.facadetemplate.user.domain.dto;

import draczek.facadetemplate.address.domain.dto.UpdateAddressDto;
import java.util.Date;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

/**
 * Update Account dto.
 */
@Value
public class UpdateAccountDto {
  String firstName;

  String lastName;

  Date birthDate;

  String phoneNumber;

  UpdateAddressDto updateAddressDto;

  @NotNull
  Integer version;
}
