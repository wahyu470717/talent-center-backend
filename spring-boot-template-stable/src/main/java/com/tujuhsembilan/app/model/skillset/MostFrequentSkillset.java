package com.tujuhsembilan.app.model.skillset;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "most_frequent_skillset")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MostFrequentSkillset {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "most_frequent_skillset_id", unique = true, nullable = false)
  private UUID mostFrequentSkillsetId;

  @ManyToOne
  @JoinColumn(name = "skillset_id", referencedColumnName = "skillset_id")
  private Skillset skillset;

  @Column(name = "counter", nullable = false)
  private int counter;

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
}
