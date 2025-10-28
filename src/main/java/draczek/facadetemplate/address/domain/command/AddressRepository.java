package draczek.facadetemplate.address.domain.command;
import draczek.facadetemplate.common.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository AddressRepository.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long>,
    JpaSpecificationExecutor<Address> {
  Optional<Address> findOneByUuid(UUID uuid);

  /**
   * Method for fetching Address.
   *
   * @param uuid UUID
   * @return Address entity
   */
  default Address get(UUID uuid) {
    return findOneByUuid(uuid)
        .orElseThrow(
            () -> new EntityNotFoundException("Could not find Address with uuid: " + uuid));
  }
}

