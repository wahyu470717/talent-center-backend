package com.tujuhsembilan.app.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Approve_RejectDto {
  private UUID talentRequestId;
  private String action;
  private String rejectReason;
}
