package com.tujuhsembilan.app.model.talent;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tujuhsembilan.app.model.employee.EmployeeStatus;
import com.tujuhsembilan.app.model.position.TalentPosition;
import com.tujuhsembilan.app.model.skillset.TalentSkillset;
import com.tujuhsembilan.app.model.talent.talent_request.TalentWishlist;

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
@Table(name = "talent")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Talent {

  @Id
  @Column(name = "talent_id", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID talentId;

  @Column(name = "talent_name", nullable = false)
  private String talentName;

  @Column(name = "talent_photo_filename", nullable = false)
  private String talentPhotoFilename;

  @Column(name = "employee_number", nullable = false)
  private String employeeNumber;

  @Column( name = "gender", nullable = false )
  private char gender;

  @Column( name = "birth_date", nullable = false )
  private Timestamp birthDate;

  @Column( name = "talent_description", nullable = false )
  private String talentDescription;

  @Column( name = "talent_cv_filename", nullable = false )
  private String talentCvFilename;

  @Column( name = "experience", nullable = false )
  private int experience;

  @Column( name = "email", nullable = false )
  private String email;

  @Column( name = "cellphone", nullable = false )
  private String cellphone;

  @Column( name = "biography_video_url", nullable = false )
  private String biographyVideoUrl;

  @Column( name = "is_add_to_list_enable", nullable = false)
  private Boolean isAddToListEnable;

  @Column( name = "talent_availability", nullable = false)
  private Boolean talentAvailability;

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

  @Column(name = "talent_cv_url")
  private String talentCvUrl;

  @Column(name = "talent_photo_url")
  private String talentPhotoUrl;

  @Column(name = "total_project_completed")
  private int totalProjectCompleted;

  @ManyToOne
  @JoinColumn(name = "talent_status_id", referencedColumnName = "talent_status_id")
  private TalentStatus talentStatus;

  @ManyToOne
  @JoinColumn(name = "employee_status_id", referencedColumnName = "employee_status_id")
  private EmployeeStatus employeeStatus;

  @ManyToOne
  @JoinColumn(name = "talent_level_id", referencedColumnName = "talent_level_id")
  private TalentLevel talentLevel;

  @OneToMany(mappedBy = "talent")
  private Set<TalentPosition> talentPositions;

  @OneToMany(mappedBy = "talent")
  private Set<TalentSkillset> talentSkillsets;

  @OneToMany(mappedBy = "talent")
  private Set<TalentWishlist> talentWishlists;
}
