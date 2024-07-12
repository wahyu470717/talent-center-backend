// package com.tujuhsembilan.app.model.client;

// import java.sql.Timestamp;
// import java.util.Set;
// import java.util.UUID;

// import org.springframework.data.annotation.CreatedDate;
// import org.springframework.data.annotation.LastModifiedDate;
// import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// import com.tujuhsembilan.app.model.employee.EmployeeStatus;
// import com.tujuhsembilan.app.model.position.TalentPosition;
// import com.tujuhsembilan.app.model.skillset.TalentSkillset;
// import com.tujuhsembilan.app.model.talent.talent_request.TalentWishlist;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.EntityListeners;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;

// import jakarta.persistence.Table;
// import jakarta.persistence.Temporal;
// import jakarta.persistence.TemporalType;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;
// import lombok.NoArgsConstructor;


// import com.tujuhsembilan.app.model.client.Client;

// @Entity
// @Table(name = "client_position")
// @EntityListeners(AuditingEntityListener.class)
// @Data
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
// public class ClientPosition {
  
//   @Id
//   @GeneratedValue(strategy = GenerationType.AUTO)
//   @Column(name = "client_position_id", unique = true, nullable = false)
//   private UUID clientPositionId;

//   @Column(name = "client_position_name",nullable = false)
//   private String clientPositionName;

//   @Column(name = "is_active")
//   private Boolean isActive;

//   @Column(name = "created_by")
//   private String createdBy;

//   @Column(name = "created_time")
//   @CreatedDate
//   @Temporal(TemporalType.TIMESTAMP)
//   private Timestamp createdTime;

//   @Column(name = "last_modified_by")
//   private String lastModifiedBy;

//   @Column(name = "last_modified_time")
//   @LastModifiedDate
//   @Temporal(TemporalType.TIMESTAMP)
//   private Timestamp lastModifiedTime;

//   @ManyToOne
//   @JoinColumn(name = "client_id", referencedColumnName = "client_id")
//   private Client client;

// }
