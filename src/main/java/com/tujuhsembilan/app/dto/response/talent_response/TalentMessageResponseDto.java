package com.tujuhsembilan.app.dto.response.talent_response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalentMessageResponseDto {
  private List<TalentResponse> data;
  private long totalData;
  private String message;
  private String status;
  private Integer statusCode;
}
