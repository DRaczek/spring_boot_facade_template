package draczek.facadetemplate.common.entity;

import draczek.facadetemplate.common.enumerated.StatusEnum;
import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Shared Audit entity, used as a super class for other entities.
 */
@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity implements Serializable {

  @CreatedDate
  @Column(name = "created_date")
  protected LocalDateTime createdDate;

  @CreatedBy
  @Column(name = "user_id_created")
  protected Long userIdCreated;

  @LastModifiedDate
  @Column(name = "last_modified_date")
  protected LocalDateTime lastModifiedDate;

  @LastModifiedBy
  @Column(name = "user_id_last_modified")
  protected Long userIdLastModified;

  @NotNull
  protected StatusEnum status;

  @Version
  @NotNull
  @Min(0)
  protected Integer version;
}