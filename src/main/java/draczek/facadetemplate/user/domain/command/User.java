package draczek.facadetemplate.user.domain.command;

import draczek.facadetemplate.common.entity.AuditableEntity;
import draczek.facadetemplate.role.domain.command.Role;
import draczek.facadetemplate.user.domain.exception.UserOptimisticLockException;

import java.sql.Types;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

/**
 * Users entity.
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "uuid", callSuper = false)
@Table(name = "users")
public class User extends AuditableEntity {

  @Id
  @Column(nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @JdbcTypeCode(Types.VARCHAR)
  @Column(unique = true, updatable = false, length = 36)
  private UUID uuid;

  @NotNull
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private Account account;

  @NotNull
  @Column(nullable = false)
  private boolean enabled;

  @Column(unique = true)
  private String username;

  @Column(name = "password_hash")
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  @NotNull
  @Column(nullable = false)
  private boolean accountNonExpired;

  @NotNull
  @Column(nullable = false)
  private boolean accountNonLocked;

  @NotNull
  @Column(nullable = false)
  private boolean credentialsNonExpired;

  /**
   * Version setter.
   */
  @Override
  public void setVersion(Integer version) {
    if (!Objects.equals(version, this.version)) {
      throw new UserOptimisticLockException();
    }
    this.version = version;
  }
}