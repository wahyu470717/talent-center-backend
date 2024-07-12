package com.tujuhsembilan.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tujuhsembilan.app.model.employee.EmployeeStatus;


public interface EmployeeStatusRepository extends JpaRepository<EmployeeStatus, UUID> {
  
}
