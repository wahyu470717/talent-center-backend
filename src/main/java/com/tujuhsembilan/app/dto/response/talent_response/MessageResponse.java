package com.tujuhsembilan.app.dto.response.talent_response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponse {
    private String message;
    private int statusCode;
    private String status;
}
