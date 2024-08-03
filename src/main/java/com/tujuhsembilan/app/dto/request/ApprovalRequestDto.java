package com.tujuhsembilan.app.dto.request;



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
  private String requestDate;
}
