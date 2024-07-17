package com.tujuhsembilan.app.dto.response.talent_response;


import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillsetResponse {
  private UUID skillsetId;
  private String skillsetName;
}
