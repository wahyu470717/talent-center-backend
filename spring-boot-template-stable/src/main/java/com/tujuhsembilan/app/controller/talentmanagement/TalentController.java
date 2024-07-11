package com.tujuhsembilan.app.controller.talentmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tujuhsembilan.app.dto.request.PageRequest;
import com.tujuhsembilan.app.dto.request.TalentRequestDto;
import com.tujuhsembilan.app.dto.response.TalentResponse;
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
  
  @GetMapping("/talents")
  public ResponseEntity<TalentResponse> getTalent(
      @ModelAttribute TalentRequestDto filter, 
      @ModelAttribute PageRequest pageRequest) {
      return talentService.getAllTalents(filter, pageRequest);
  }
}
  