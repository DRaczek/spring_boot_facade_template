package draczek.facadetemplate.user.domain.command;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Account's repository.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
