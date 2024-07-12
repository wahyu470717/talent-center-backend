package com.tujuhsembilan.app.dto.response;

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
  private String talentPhoto;
  private String talentName;
  private String talentStatus;
  private String nip;
  private String sex;
  private String dob;
  private String talentDescription;
  private String cv;
  private String talentExperience;
  private String talentLevel;
  private int projectCompleted;
  private int totalRequested;
  // private List<PositionResponseDto> positions;
  // private List<SkillsetResponseDto> skillSets;
  private String email;
  private String cellphone;
  private String employeeStatus;
  private String talentAvailability;
  private String videoUrl;
}
