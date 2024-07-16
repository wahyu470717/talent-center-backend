package com.tujuhsembilan.app.model.user;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
  @Column(name = "role_id", unique = true, nullable = false)
  private UUID roleId;

  @Column( name = "role_name" , nullable = false)
  private String roleName;

  @Column( name = "is_active")
  private Boolean isActive;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "created_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Timestamp createdTime;

  @Column(name = "last_modified_by")
  private String lastModifiedBy;

  @Column(name = "last_modified_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Timestamp lastModifiedTime;

  @OneToMany(mappedBy = "role")
  private Set<User> users;
}
