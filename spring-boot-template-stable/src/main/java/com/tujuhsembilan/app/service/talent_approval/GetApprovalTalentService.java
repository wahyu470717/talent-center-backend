package com.tujuhsembilan.app.service.talent_approval;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tujuhsembilan.app.dto.request.ApprovalRequestDto;
import com.tujuhsembilan.app.dto.request.PageRequest;
import com.tujuhsembilan.app.dto.response.approval_talent_response.ApprovalTalentResponse;
import com.tujuhsembilan.app.model.talent.talent_request.TalentRequest;
import com.tujuhsembilan.app.repository.approval_talent_repo.TalentRequestRepository;

@Service
@Transactional
public class GetApprovalTalentService {

  @Autowired
  private TalentRequestRepository talentRequestRepository;

  public ResponseEntity<List<ApprovalTalentResponse>> getAllApprovalTalent(ApprovalRequestDto filter,
      PageRequest pageRequest) {

    try {
      Pageable pageable = pageRequest.getPageApproval();
      List<TalentRequest> talentApproval = talentRequestRepository.findApprovalTalent(pageable).getContent();

      List<ApprovalTalentResponse> approvalTalentResponses = talentApproval.stream().map(talentRequest -> {
        ApprovalTalentResponse response = new ApprovalTalentResponse();
        response.setTalentRequestId(talentRequest.getTalentRequestId());
        response.setAgencyName(talentRequest.getTalentWishlist().getClient().getAgencyName());
        response.setRequestDate(talentRequest.getRequestDate());
        response.setTalentName(talentRequest.getTalentWishlist().getTalent().getTalentName());
        response.setApprovalStatus(talentRequest.getTalentRequestStatus().getTalentRequestStatusName());
        return response;
      }).collect(Collectors.toList());

      return ResponseEntity.ok(approvalTalentResponses);
    } catch (Exception e) {
      return ResponseEntity.status(500).body(null);
    }
  }
}
