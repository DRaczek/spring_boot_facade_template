package draczek.facadetemplate.user.domain.command;

import draczek.facadetemplate.address.domain.command.Address;
import draczek.facadetemplate.common.entity.AuditableEntity;
import draczek.facadetemplate.user.domain.exception.UserOptimisticLockException;

import java.sql.Types;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
 * Account entity.
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "uuid", callSuper = false)
@Table(name = "accounts")
public class Account extends AuditableEntity {

  @Id
  @Column(nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @JdbcTypeCode(Types.VARCHAR)
  @Column(unique = true, updatable = false, length = 36)
  private UUID uuid;

  @NotNull
  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  private String firstName;

  private String lastName;

  private Date birthDate;

  private String phoneNumber;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "address_id")
  private Address address;

  private String profilePictureSource;

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
