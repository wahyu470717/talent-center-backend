package com.tujuhsembilan.app.model.client;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tujuhsembilan.app.model.user.User;

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
@Table(name = "client")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Client {
  
  @Id
  @Column(name = "client_id", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID clientId;

  @OneToOne(mappedBy = "client")
  private ClientPosition clientPosition;

  @OneToOne(mappedBy = "client")
  private User user;

  @Column(name = "client_name")
  private String clientName;

  @Column(name = "gender")
  private String gender;

  @Column(name = "birth_date")
  private Timestamp birthDate;

  @Column(name = "email")
  private String email;

  @Column(name = "agency_name")
  private String agencyName;

  @Column(name = "agency_address")
  private String agencyAddress;

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

  @Column(name = "sex")
  private String sex;

  @Column(name = "last_mapped_id")
  private int lastMappedId;

  @OneToMany(mappedBy = "client")
  private Set<ClientPosition> talentWishlists;

}
