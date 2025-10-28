package draczek.facadetemplate.role.domain.command;

import draczek.facadetemplate.common.entity.AuditableEntity;
import draczek.facadetemplate.role.domain.enumerated.RoleEnum;
import java.sql.Types;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

/**
 * Role entity.
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "uuid", callSuper = false)
@Table(name = "roles")
public class Role extends AuditableEntity {

  @Id
  @Column(nullable = false, updatable = false)
  private Long id;

  @NotNull
  @JdbcTypeCode(Types.VARCHAR)
  @Column(unique = true, updatable = false, length = 36)
  private UUID uuid;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, updatable = false)
  private RoleEnum name;
}