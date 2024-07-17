package com.tujuhsembilan.app.model.position;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tujuhsembilan.app.model.talent.Talent;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Table(name = "talent_position")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TalentPosition implements Serializable{

  @EmbeddedId
  private TalentPositionId id;

  @MapsId("talendId")
  @ManyToOne
  @JoinColumn(name = "talent_id", referencedColumnName = "talent_id", nullable = false)
  private Talent talent;

  @MapsId("positionId")
  @ManyToOne
  @JoinColumn(name = "position_id", referencedColumnName = "position_id", nullable = false)
  private Position position;

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
