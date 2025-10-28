package draczek.facadetemplate.userActionToken.domain.command;

import draczek.facadetemplate.user.domain.command.User;
import draczek.facadetemplate.userActionToken.domain.enumerated.UserActionTokenEnum;
import java.io.Serializable;
import java.time.LocalDateTime;
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
import org.springframework.data.annotation.CreatedDate;

/**
 * UserActionTokenHistory entity.
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "key", callSuper = false)
@Table(name = "users_actions_tokens_history")
class UserActionTokenHistory implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long id;

  @NotNull
  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  @NotNull
  private String key;

  @NotNull
  @CreatedDate
  private LocalDateTime createdDate;

  @NotNull
  private UserActionTokenEnum action;

  private LocalDateTime usedDate;

  @NotNull
  private LocalDateTime deletedDate;
}
