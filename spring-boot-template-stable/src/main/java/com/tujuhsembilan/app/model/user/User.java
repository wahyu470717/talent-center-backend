package com.tujuhsembilan.app.model.user;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tujuhsembilan.app.model.client.Client;

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
@Table(name = "user")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {
  
  @Id
  @Column(name = "user_id", unique = true)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID userId;

  @Column(name = "username")
  private String username;

  @Column(name = "email")
  private String email;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "password")
  private String password;

  @Column(name = "is_active")
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

  @ManyToOne
  @JoinColumn(name = "role_id", referencedColumnName = "role_id")
  private Role role;

  // @OneToOne
  // @JoinColumn(name = "client_id", referencedColumnName = "client_id")
  // private Client client;

  // @OneToMany(mappedBy = "user")
  // private Set<Client> clients;
}
