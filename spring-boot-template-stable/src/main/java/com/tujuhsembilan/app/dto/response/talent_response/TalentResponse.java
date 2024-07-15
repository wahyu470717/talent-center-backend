package com.tujuhsembilan.app.dto.response.talent_response;

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
public class TalentResponse {
  private UUID talentId;
  private String talentPhotoUrl;
  private String talentName;
  private String employeeStatus;
  private Boolean talentAvailability;
  private Integer talentExperience;
  private String talentLevel;
  private String talentStatus;
  private List<PositionResponse> position;
  private List<SkillsetResponse> skillSet; 
}
