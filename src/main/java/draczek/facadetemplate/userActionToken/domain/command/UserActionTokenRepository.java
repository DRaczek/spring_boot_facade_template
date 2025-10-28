package draczek.facadetemplate.userActionToken.domain.command;

import draczek.facadetemplate.userActionToken.domain.exception.UserActionTokenNotFoundException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserActionToken facade's repository.
 */
@Repository
interface UserActionTokenRepository extends JpaRepository<UserActionToken, Long> {
  Optional<UserActionToken> findByKey(String key);

  /**
   * Method for fetching UserActionToken instance based on its key.
   *
   * @param key token key
   * @return found UserActionToken
   */
  default UserActionToken get(String key) {
    return findByKey(key).orElseThrow(() -> new UserActionTokenNotFoundException(key));
  }
}
