package draczek.facadetemplate.user.domain.command;

import draczek.facadetemplate.common.enumerated.StatusEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

/**
 * Repository User.
 */
@Repository
interface UserRepository extends JpaRepository<User, Long>,
    JpaSpecificationExecutor<User> {

  Optional<User> findOneByUsernameAndStatus(String username, StatusEnum status);

  /**
   * Method for fetching User.
   *
   * @param username user name
   * @param status   entity status
   * @return User entity
   */
  default User get(String username, StatusEnum status) {
    return findOneByUsernameAndStatus(username, status)
        .orElseThrow(
            () -> new UsernameNotFoundException(
                String.format("Could not find User with username: %s and status: %s", username,
                    status)));
  }
}