package com.tujuhsembilan.app.dto.response.approval_talent_response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MessageApprovalResponse {
    private List<ApprovalTalentResponse> data;
    private  long total;
    private String message;
    private int statusCode;
    private String status;
}
