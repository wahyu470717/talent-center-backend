package com.tujuhsembilan.app.model.talent;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "talent_level")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TalentLevel {

  @Id
  @Column(name = "talent_level_id", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID talentLevelId;

  @Column(name = "talent_level_name", length = 50)
  @Size(max = 50)
  private String talentLevelName;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "created_time")
  private Timestamp createdTime;

  @Column(name = "last_modified_by")
  private String lastModifiedBy;

  @Column(name = "last_modified_time")
  private Timestamp lastModifiedTime;

  // Relasi dengan entitas Talent
  @OneToMany(mappedBy = "talentLevel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<Talent> talents;

}
