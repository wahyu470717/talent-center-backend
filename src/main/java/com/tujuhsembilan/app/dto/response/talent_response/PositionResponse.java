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
public class PositionResponse {
    private UUID positionId;
    private String positionName;
}