package com.tujuhsembilan.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalentSpesificationRequest {
  private String talentName;
  private String talentLevel;
  private Integer talentExperience;
  private String talentStatus;
  private String employeeStatus;
}
