package com.tujuhsembilan.app.dto.request;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalRequestDto {
  private String status;
  private String instansi;
  private String talent;
  private Timestamp dateRequest;
}
