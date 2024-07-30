package com.tujuhsembilan.app.repository.approval_talent_repo;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tujuhsembilan.app.model.talent.talent_request.TalentRequest;

public interface TalentRequestRepository extends JpaRepository<TalentRequest, UUID>, JpaSpecificationExecutor<TalentRequest> {

  @Query("SELECT t FROM TalentRequest t " +
      "JOIN t.talentRequestStatus ts " +
      "JOIN t.talentWishlist tw " +
      "JOIN tw.talent twt " +
      "GROUP BY t, ts.talentRequestStatusName, twt.talentName")
  Page<TalentRequest> findApprovalTalent(Pageable pageable);


    @Query("SELECT COUNT(tr) FROM TalentRequest tr WHERE tr.talentWishlist.talent.talentId = :talentId")
    Integer countRequestsByTalentId(@Param("talentId") UUID talentId);
}
