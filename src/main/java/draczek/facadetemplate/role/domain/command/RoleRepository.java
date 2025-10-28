package draczek.facadetemplate.role.domain.command;

import draczek.facadetemplate.role.domain.enumerated.RoleEnum;
import draczek.facadetemplate.role.domain.exception.RoleNotFoundException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Role repository.
 */
@Repository
interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(RoleEnum name);

  /**
   * Method for fetching Role's entity based on its name.
   *
   * @param name Role enum
   * @return Role entity
   */
  default Role get(RoleEnum name) {
    return findByName(name).orElseThrow(() -> new RoleNotFoundException(name.getValue()));
  }
}
