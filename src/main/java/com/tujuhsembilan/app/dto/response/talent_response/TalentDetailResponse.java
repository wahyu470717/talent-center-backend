package com.tujuhsembilan.app.dto.response.talent_response;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalentDetailResponse {
  private UUID talentId;
  private String talentPhotoUrl;
  private String talentName;
  private String talentStatus;
  private String nip; //employeeNumber
  private Character sex;
  private Timestamp dob; //tanggal lahir
  private String talentDescription;
  private String cv;
  private Integer talentExperience;
  private String talentLevel;
  private Integer projectCompleted;
  private Integer totalRequested;
  private List<PositionResponse> position;
  private List<SkillsetResponse> skillSet;
  private String email;
  private String cellphone;
  private String employeeStatus;
  private Boolean talentAvailability;
  private String videoUrl;
}
