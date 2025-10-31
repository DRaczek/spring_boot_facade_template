package draczek.facadetemplate.user.domain.command;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository RefreshTokenRepository.
 */
@Repository
interface RefreshTokenHistoryRepository extends JpaRepository<RefreshTokenHistory, Long>,
    JpaSpecificationExecutor<RefreshToken> {
}