package com.tujuhsembilan.app.dto.response.approval_talent_response;

import java.sql.Timestamp;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalTalentResponse {
  private UUID talentRequestId;
  private String agencyName;
  private Timestamp requestDate;
  private String  talentName;
  private String approvalStatus;
}
