package com.tujuhsembilan.app.model.talent.talent_request;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tujuhsembilan.app.model.talent.Talent;

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
@Table(name = "talent_wishlist")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TalentWishlist {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "talent_wishlist_id", unique = true, nullable = false)
  private UUID talentWishlistId;

  @ManyToOne
  @JoinColumn(name = "talent_id", referencedColumnName = "talent_id", nullable = false)
  private Talent talent;

  // @ManyToOne
  // @JoinColumn(name = "client_id", referencedColumnName = "client_id", nullable = false)
  @Column(name = "client_id")
  private UUID client;

  @Column(name = "wishlist_date", nullable = false)
  private Timestamp wishlistDate;

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
}
