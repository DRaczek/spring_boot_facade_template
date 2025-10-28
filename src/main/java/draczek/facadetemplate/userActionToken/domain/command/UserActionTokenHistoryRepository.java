package draczek.facadetemplate.userActionToken.domain.command;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserActionTokenHistory repository.
 */
@Repository
interface UserActionTokenHistoryRepository
    extends JpaRepository<UserActionTokenHistory, Long> {
}
