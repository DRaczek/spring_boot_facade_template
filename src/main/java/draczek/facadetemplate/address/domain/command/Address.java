package draczek.facadetemplate.address.domain.command;

import draczek.facadetemplate.address.domain.exception.AddressOptimisticLockException;
import draczek.facadetemplate.common.entity.AuditableEntity;

import java.sql.Types;
import java.util.Objects;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.JdbcTypeCode;

/**
 * addresses table entity.
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "uuid", callSuper = false)
@Table(name = "addresses")
@Slf4j
public class Address extends AuditableEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long id;

  @NotNull
  @JdbcTypeCode(Types.VARCHAR)
  @Column(unique = true, updatable = false, length = 36)
  private UUID uuid;

  private String city;

  private String postalName;

  private String postalCode;

  private String street;

  private String streetNumber;

  private String apartmentNumber;

  private String country;

  /**
   * Version setter.
   */
  @Override
  public void setVersion(Integer version) {
    log.info(version + ", " + this.version);
    if (!Objects.equals(version, this.version)) {
      throw new AddressOptimisticLockException();
    }
    this.version = version;
  }

}
