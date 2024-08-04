package com.tujuhsembilan.app.controller.talentmanagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tujuhsembilan.app.dto.request.ApprovalRequestDto;
import com.tujuhsembilan.app.dto.request.Approve_RejectDto;
import com.tujuhsembilan.app.dto.request.PageRequest;
import com.tujuhsembilan.app.dto.response.approval_talent_response.MessageApprovalResponse;
import com.tujuhsembilan.app.dto.response.talent_response.MessageResponse;
import com.tujuhsembilan.app.service.talent_approval.ApproveOrRejectTalentService;
import com.tujuhsembilan.app.service.talent_approval.GetApprovalTalentService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/talent-management")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApprovalTalentController {

    @Autowired
    private GetApprovalTalentService getApprovalTalentService;

    @Autowired
    private ApproveOrRejectTalentService approveTalentService;

    // GET_ALL_TALENTS_APPROVAL
    @GetMapping("/talent-approvals")
    public ResponseEntity<MessageApprovalResponse> getTalentApproval(
            @ModelAttribute ApprovalRequestDto filter,
            PageRequest pageRequest) {
        return getApprovalTalentService.getAllApprovalTalent(filter, pageRequest);
    }
    //PUT_APPROVE_OR_REJECT_TALENT
    @PutMapping("/talent-approvals")
    public ResponseEntity<MessageResponse> approveTalent(
            @RequestBody Approve_RejectDto request) {
        return approveTalentService.approveTalentStatus(request);
    }


}
