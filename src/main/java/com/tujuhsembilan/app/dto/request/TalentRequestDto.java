package com.tujuhsembilan.app.dto.request;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import com.tujuhsembilan.app.dto.request.id_dto.PositionIdDto;
import com.tujuhsembilan.app.dto.request.id_dto.SkillsetIdDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalentRequestDto {
  private String imageFilename;
  private String talentName;
  private UUID talentStatusId;
  private String nip;
  private Character gender;
  private Timestamp dob;
  private String talentDescription;
  private String cv;
  private UUID employeeStatusId;
  private Boolean talentAvailability;
  private Integer talentExperience;
  private UUID talentLevelId;
  private Integer projectCompleted;
  private String email;
  private String cellphone;
  private List<PositionIdDto> positionIds;
  private List<SkillsetIdDto> skillsetIds;
}
