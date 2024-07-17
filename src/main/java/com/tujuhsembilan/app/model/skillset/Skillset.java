package com.tujuhsembilan.app.model.skillset;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "skillset")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Skillset {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "skillset_id", unique = true, nullable = false)
  private UUID skillsetId;

  @ManyToOne
  @JoinColumn(name = "skillset_type_id", referencedColumnName = "skillset_type_id")
  private SkillsetType skillsetType;

  @Column(name = "skillset_name", nullable = false)
  private String skillsetName;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "created_time")
  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Timestamp createdTime;

  @Column(name = "last_modified_by")
  private String lastModifiedBy;

  @Column(name = "last_modified_time")
  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Timestamp lastModifiedTime;

  @OneToMany(mappedBy = "skillset", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<TalentSkillset> talentSkillsets;
}
