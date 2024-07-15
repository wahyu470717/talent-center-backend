package com.tujuhsembilan.app.controller.talentmanagement;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tujuhsembilan.app.dto.request.PageRequest;
import com.tujuhsembilan.app.dto.request.TalentRequest;
import com.tujuhsembilan.app.dto.response.employee_response.EmployeeStatusResponse;
import com.tujuhsembilan.app.dto.response.talent_response.MessageResponse;
import com.tujuhsembilan.app.dto.response.talent_response.TalentDetailResponse;
import com.tujuhsembilan.app.dto.response.talent_response.TalentResponse;
import com.tujuhsembilan.app.service.CreateTalentService;
import com.tujuhsembilan.app.service.EditTalentService;
import com.tujuhsembilan.app.service.EmployeeStatusService;
import com.tujuhsembilan.app.service.TalentService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/talent-management")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TalentController {

    @Autowired
    private TalentService talentService;

    @Autowired
    private EmployeeStatusService employeeStatusService;

    @Autowired
    private CreateTalentService createTalentService;

    @Autowired
    private EditTalentService editTalentService;

    // GET_ALL_TALENTS
    @GetMapping("/talents")
    public ResponseEntity<List<TalentResponse>> getTalent(
            @ModelAttribute TalentRequest filter,
            PageRequest pageRequest) {
        return talentService.getAllTalents(filter, pageRequest);
    }

    // GET_DETAIL_TALENTS
    @GetMapping("/talents/{talentId}")
    public ResponseEntity<TalentDetailResponse> getTalentById(@PathVariable UUID talentId) {
        return talentService.getTalentDetailById(talentId);
    }

    // GET_EMPLOYEE_STATUS
    @GetMapping("/employee-status-option-lists")
    public ResponseEntity<List<EmployeeStatusResponse>> getEmployeeStatusOptions() {
        return employeeStatusService.getAllEmployeeStatus();
    }

    // POST_SAVE_DATA_TALENT
    @PostMapping(path = { "/talents" }, consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<MessageResponse> createTalent(
            @RequestPart("request") TalentRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        MessageResponse response = createTalentService.createTalent(request, file);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
   // PUT_EDIT_DATA_TALENT
    @PutMapping("/talents/{talentId}")
    public ResponseEntity<MessageResponse> editTalent(
            @PathVariable UUID talentId,
            @RequestPart("request") TalentRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        MessageResponse response = editTalentService.editTalent(talentId, request, file);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
