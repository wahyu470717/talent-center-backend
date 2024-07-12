package com.tujuhsembilan.app.dto.response.employee_response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeStatusResponse {
    private UUID employeeStatusId;
    private String employeeStatusName;
}