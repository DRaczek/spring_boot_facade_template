package draczek.facadetemplate.user.domain.dto;

import draczek.facadetemplate.address.domain.dto.AddressDto;
import java.util.Date;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

/**
 * Account dto.
 */
@Value
public class AccountDto {
  String firstName;

  String lastName;

  Date birthDate;

  String phoneNumber;

  AddressDto address;

  String profilePictureSource;

  @NotNull
  Integer version;
}
