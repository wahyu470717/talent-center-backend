package com.tujuhsembilan.app.dto.response;

import java.util.List;
import java.util.UUID;

import com.tujuhsembilan.app.model.employee.EmployeeStatus;
import com.tujuhsembilan.app.model.talent.TalentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalentDto {
  private UUID talentId;
  private String talentPhotoUrl;
  private String talentName;
  private TalentStatus talentStatus;
  private EmployeeStatus employeeStatus;
  private Boolean talentAvailability;
  private Integer talentExperience;
  private String talentLevel;
  private List<PositionResponseDto> position;
  private List<SkillsetResponseDto> skillSet; 
}
