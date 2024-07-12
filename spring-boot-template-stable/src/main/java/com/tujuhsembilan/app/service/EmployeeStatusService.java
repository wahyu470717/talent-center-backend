package com.tujuhsembilan.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tujuhsembilan.app.dto.response.employee_response.EmployeeStatusResponse;
import com.tujuhsembilan.app.model.employee.EmployeeStatus;
import com.tujuhsembilan.app.repository.EmployeeStatusRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmployeeStatusService {

  @Autowired
  private EmployeeStatusRepository employeeStatusRepository;

  @Autowired
  private ModelMapper modelMapper;
  
  public ResponseEntity<List<EmployeeStatusResponse>> getAllEmployeeStatus() {
    List<EmployeeStatus> employeeStatuses = employeeStatusRepository.findAll();
    List<EmployeeStatusResponse> response = employeeStatuses.stream()
            .map(status -> modelMapper.map(status, EmployeeStatusResponse.class))
            .collect(Collectors.toList());

    return ResponseEntity.ok(response);
  }
}
