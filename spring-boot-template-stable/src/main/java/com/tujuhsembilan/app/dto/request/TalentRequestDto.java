package com.tujuhsembilan.app.dto.request;

import java.util.UUID;

import com.tujuhsembilan.app.model.employee.EmployeeStatus;
import com.tujuhsembilan.app.model.position.Position;
import com.tujuhsembilan.app.model.skillset.Skillset;
import com.tujuhsembilan.app.model.talent.TalentLevel;
import com.tujuhsembilan.app.model.talent.TalentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalentRequestDto {
  private UUID talentId;
  private String talentName;
  private String talentStatus;
  private String employeeStatus;
  private Boolean talentAvailability;
  private Integer talentExperience;
  private String talentLevel;
  private Position position;
  private Skillset skillSet;
}
