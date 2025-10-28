package draczek.facadetemplate.address.domain.command;

import draczek.facadetemplate.address.domain.dto.AddressDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

/**
 * Mapper Address.
 */
@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AddressMapper {
  @Mappings({
  })
  AddressDto toDto(Address address);
}
