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
  private String employeeStatus;
  private String nip;
  private String sex;
  private Integer talentExperience; 
  private String talentLevel;
  private String talentStatus;
  private Timestamp dob;
  private String talentDescription;
  private String cv;
  private Integer projectCompleted;
  private String email;
  private String cellphone;
  private String videoUrl;
  private List<PositionResponse> positions;
  private List<SkillsetResponse> skillSets;
}
