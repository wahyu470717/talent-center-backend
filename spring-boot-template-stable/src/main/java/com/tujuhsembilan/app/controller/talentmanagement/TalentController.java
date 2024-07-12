package com.tujuhsembilan.app.controller.talentmanagement;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tujuhsembilan.app.dto.request.PageRequest;
import com.tujuhsembilan.app.dto.request.TalentRequestDto;
import com.tujuhsembilan.app.dto.response.TalentDetailResponse;
import com.tujuhsembilan.app.dto.response.TalentResponse;
import com.tujuhsembilan.app.dto.response.employee_response.EmployeeStatusResponse;
import com.tujuhsembilan.app.service.EmployeeStatusService;
import com.tujuhsembilan.app.service.TalentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/talent-management")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TalentController {
  
  @Autowired
  private TalentService talentService;

  @Autowired
  private EmployeeStatusService employeeStatusService;
  
  @GetMapping("/talents")
  public ResponseEntity<TalentResponse> getTalent(
      @ModelAttribute TalentRequestDto filter, 
      @ModelAttribute PageRequest pageRequest) {
      return talentService.getAllTalents(filter, pageRequest);
  }

    @GetMapping("/talents/{talentId}")
    public ResponseEntity<TalentDetailResponse> getTalentById(@PathVariable UUID talentId) {
        return talentService.getTalentById(talentId);
    }

    //get search data master level talent
    @GetMapping("/employee-status-option-lists")
    public ResponseEntity<List<EmployeeStatusResponse>> getEmployeeStatusOptions() {
        return employeeStatusService.getAllEmployeeStatus();
    }
  
}
  