package com.tujuhsembilan.app.service.talent_approval;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tujuhsembilan.app.dto.request.Approve_RejectDto;
import com.tujuhsembilan.app.dto.response.talent_response.MessageResponse;
import com.tujuhsembilan.app.model.talent.talent_request.TalentRequest;
import com.tujuhsembilan.app.model.talent.talent_request.TalentRequestStatus;
import com.tujuhsembilan.app.repository.approval_talent_repo.TalentRequestRepository;
import com.tujuhsembilan.app.repository.approval_talent_repo.TalentRequestStatusRepository;

@Service
@Transactional
public class ApproveOrRejectTalentService {

  @Autowired
  private TalentRequestRepository talentRequestRepository;

  @Autowired
  private TalentRequestStatusRepository talentRequestStatusRepository;

  public ResponseEntity<MessageResponse> approveTalentStatus(Approve_RejectDto request) {
    try {
      TalentRequest talentRequest = talentRequestRepository.findById(request.getTalentRequestId()).orElse(null);
      if (talentRequest == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new MessageResponse(0, "Talent request not found", HttpStatus.NOT_FOUND.value(), "FAILED"));
      }

      String statusName;
      if ("approve".equalsIgnoreCase(request.getAction())) {
        statusName = "Approved";
        talentRequest.setRequestRejectReason(null);
      } else if ("reject".equalsIgnoreCase(request.getAction())) {
        statusName = "Rejected";
        talentRequest.setRequestRejectReason(request.getRejectReason());
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new MessageResponse(0, "Invalid action", HttpStatus.BAD_REQUEST.value(), "FAILED"));
      }

      // Get active status
      TalentRequestStatus status = talentRequestStatusRepository.findByTalentRequestStatusNameAndIsActive(statusName, true)
          .orElse(null);

      if (status == null) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new MessageResponse(0, "Active status not found", HttpStatus.INTERNAL_SERVER_ERROR.value(), "FAILED"));
      }

      talentRequest.setTalentRequestStatus(status);
      talentRequestRepository.save(talentRequest);
      return ResponseEntity.ok(new MessageResponse(0, "Talent status updated successfully", HttpStatus.OK.value(), "SUCCESS"));

    } catch (Exception e) {
      // Log the full stack trace for debugging
      e.printStackTrace(); 
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new MessageResponse(0, "Error processing talent request", HttpStatus.INTERNAL_SERVER_ERROR.value(), "FAILED"));
    }
  }
}
