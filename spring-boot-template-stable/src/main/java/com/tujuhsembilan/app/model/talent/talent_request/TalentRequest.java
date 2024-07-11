package com.tujuhsembilan.app.model.talent.talent_request;

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
@Table(name = "talent_request")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TalentRequest {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "talent_request_id", unique = true, nullable = false)
  private UUID talentRequestId;

  @ManyToOne
  @JoinColumn(name = "talent_request_status_id", referencedColumnName = "talent_request_status_id", nullable = false)
  private TalentRequestStatus talentRequestStatus;

  @ManyToOne
  @JoinColumn(name = "talent_wishlist_id", referencedColumnName = "talent_wishlist_id", nullable = false)
  private TalentWishlist talentWishlist;

  @Column(name = "request_date", nullable = false)
  private Timestamp requestDate;

  @Column(name = "request_reject_reason")
  private String requestRejectReason;
  
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
