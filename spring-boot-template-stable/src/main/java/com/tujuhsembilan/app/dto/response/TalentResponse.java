package com.tujuhsembilan.app.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalentResponse {
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private List<TalentDto> data;
}
