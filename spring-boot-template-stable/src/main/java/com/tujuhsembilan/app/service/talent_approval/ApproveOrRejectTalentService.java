package com.tujuhsembilan.app.service.talent_approval;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tujuhsembilan.app.dto.request.Approve_RejectDto;
import com.tujuhsembilan.app.dto.response.talent_response.MessageResponse;
import com.tujuhsembilan.app.model.talent.talent_request.TalentRequest;
import com.tujuhsembilan.app.repository.approval_talent_repo.TalentRequestRepository;

@Service
@Transactional
public class ApproveOrRejectTalentService {

  @Autowired
  private TalentRequestRepository talentRequestRepository;

  public ResponseEntity<MessageResponse> approveTalentStatus(Approve_RejectDto request) {
    try {
      TalentRequest talentRequest = talentRequestRepository.findById(request.getTalentRequestId()).orElse(null);
      if (talentRequest == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new MessageResponse("Talent request not found", HttpStatus.NOT_FOUND.value(), "FAILED"));
      }

      // Assuming 'action' field is used to determine approval
      if ("approve".equalsIgnoreCase(request.getAction())) {
        talentRequest.getTalentRequestStatus().setTalentRequestStatusName("Approved");
        talentRequest.setRequestRejectReason(null);
        talentRequestRepository.save(talentRequest);
        return ResponseEntity.ok(new MessageResponse("Talent approved successfully", HttpStatus.OK.value(), "SUCCESS"));
      } else if ("reject".equalsIgnoreCase(request.getAction())) {
        talentRequest.getTalentRequestStatus().setTalentRequestStatusName("Rejected");
        talentRequest.setRequestRejectReason(request.getRejectReason());
        talentRequestRepository.save(talentRequest);
        return ResponseEntity.ok(new MessageResponse("Talent rejected successfully", HttpStatus.OK.value(), "SUCCESS"));
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new MessageResponse("Invalid action", HttpStatus.BAD_REQUEST.value(), "FAILED"));
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new MessageResponse("Error processing talent request", HttpStatus.INTERNAL_SERVER_ERROR.value(),
              "FAILED"));
    }
  }

}
