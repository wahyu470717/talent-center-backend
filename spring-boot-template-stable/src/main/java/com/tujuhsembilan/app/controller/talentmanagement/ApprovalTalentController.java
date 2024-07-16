package com.tujuhsembilan.app.controller.talentmanagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tujuhsembilan.app.dto.request.ApprovalRequestDto;
import com.tujuhsembilan.app.dto.request.PageRequest;
import com.tujuhsembilan.app.dto.response.approval_talent_response.ApprovalTalentResponse;
import com.tujuhsembilan.app.service.talent_approval.ApprovalTalentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/talent-management")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApprovalTalentController {

    @Autowired
    private ApprovalTalentService approvalTalentService;

    // // GET_ALL_TALENTS_APPROVAL
    @GetMapping("/talent-approvals")
    public ResponseEntity<List<ApprovalTalentResponse>> getTalentApproval(
            @ModelAttribute ApprovalRequestDto filter,
            PageRequest pageRequest) {
        return approvalTalentService.getAllApprovalTalent(filter, pageRequest);
    }

    // // GET_DETAIL_TALENTS
    // @GetMapping("/talents/{talentId}")
    // public ResponseEntity<TalentDetailResponse> getTalentById(@PathVariable UUID
    // talentId) {
    // return talentService.getTalentDetailById(talentId);
    // }

    // // GET_EMPLOYEE_STATUS
    // @GetMapping("/employee-status-option-lists")
    // public ResponseEntity<List<EmployeeStatusResponse>>
    // getEmployeeStatusOptions() {
    // return employeeStatusService.getAllEmployeeStatus();
    // }

    // // POST_SAVE_DATA_TALENT
    // @PostMapping(path = { "/talents" }, consumes = {
    // MediaType.APPLICATION_JSON_VALUE,
    // MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
    // MediaType.APPLICATION_JSON_VALUE })
    // public ResponseEntity<MessageResponse> createTalent(
    // @RequestPart("request") TalentRequest request,
    // @RequestPart(value = "file", required = false) MultipartFile file) {
    // MessageResponse response = createTalentService.createTalent(request, file);
    // return ResponseEntity.status(response.getStatusCode()).body(response);
    // }

    // // PUT_EDIT_DATA_TALENT
    // @PutMapping("/talents/{talentId}")
    // public ResponseEntity<MessageResponse> editTalent(
    // @PathVariable UUID talentId,
    // @RequestPart("request") TalentRequest request,
    // @RequestPart(value = "file", required = false) MultipartFile file) {
    // MessageResponse response = editTalentService.editTalent(talentId, request,
    // file);
    // return ResponseEntity.status(response.getStatusCode()).body(response);
    // }
}
