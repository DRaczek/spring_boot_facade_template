package draczek.facadetemplate.user.domain.command;

import java.util.List;
import java.util.Optional;

import draczek.facadetemplate.user.domain.exception.RefreshTokenNotFoundException;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository RefreshToken.
 */
@Repository
interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>,
    JpaSpecificationExecutor<RefreshToken> {

  List<RefreshToken> findByUser(User user);

  Optional<RefreshToken> findByToken(String token);

  /**
   * Method for RefreshToken retrieving.
   *
   * @param refreshToken token
   * @return created RefreshToken entity
   */
  default RefreshToken get(@NotBlank String refreshToken) {
    return findByToken(refreshToken).orElseThrow(() -> new RefreshTokenNotFoundException(refreshToken));
  }
}