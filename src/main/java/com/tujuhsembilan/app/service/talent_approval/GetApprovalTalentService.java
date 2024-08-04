package com.tujuhsembilan.app.service.talent_approval;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tujuhsembilan.app.dto.request.ApprovalRequestDto;
import com.tujuhsembilan.app.dto.request.PageRequest;
import com.tujuhsembilan.app.dto.response.approval_talent_response.ApprovalTalentResponse;
import com.tujuhsembilan.app.dto.response.approval_talent_response.MessageApprovalResponse;
import com.tujuhsembilan.app.model.talent.talent_request.TalentRequest;
import com.tujuhsembilan.app.repository.approval_talent_repo.TalentRequestRepository;
import com.tujuhsembilan.app.service.specifications.ApprovalTalentSpecification;

@Service
@Transactional
public class GetApprovalTalentService {

    @Autowired
    private TalentRequestRepository talentRequestRepository;

    public ResponseEntity<MessageApprovalResponse> getAllApprovalTalent(ApprovalRequestDto filter,
            PageRequest pageRequest) {

        try {
            Pageable pageable = pageRequest.getPageApproval();
            Specification<TalentRequest> spec = ApprovalTalentSpecification.talentFilter(filter);
            Page<TalentRequest> talentApprovalPage = talentRequestRepository.findAll(spec, pageable);

            List<ApprovalTalentResponse> approvalTalentResponses = talentApprovalPage.stream().map(talentRequest -> {
                ApprovalTalentResponse response = new ApprovalTalentResponse();
                response.setTalentRequestId(talentRequest.getTalentRequestId());
                response.setAgencyName(talentRequest.getTalentWishlist().getClient().getAgencyName());
                response.setRequestDate(talentRequest.getRequestDate());
                response.setTalentName(talentRequest.getTalentWishlist().getTalent().getTalentName());
                response.setApprovalStatus(talentRequest.getTalentRequestStatus().getTalentRequestStatusName());
                return response;
            }).collect(Collectors.toList());

            // Create the MessageApprovalResponse
            MessageApprovalResponse messageResponse = MessageApprovalResponse.builder()
                    .data(approvalTalentResponses)
                    .total(talentApprovalPage.getTotalElements())
                    .message("Success")
                    .statusCode(200)
                    .status("OK")
                    .build();

            return ResponseEntity.ok(messageResponse);
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
