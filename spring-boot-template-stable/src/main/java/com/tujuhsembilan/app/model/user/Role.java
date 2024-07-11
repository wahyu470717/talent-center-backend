package com.tujuhsembilan.app.model.user;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Role {
  
  @Id
  private UUID roleId;

  private String roleName;
  private Boolean isActive;
  private String createdBy;
  private Timestamp createdTime;
  private String lastModifiedBy;
  private Timestamp lastModifiedTime;
}
