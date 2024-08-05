package com.tujuhsembilan.app.repository.approval_talent_repo;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tujuhsembilan.app.model.talent.talent_request.TalentRequestStatus;

public interface TalentRequestStatusRepository extends JpaRepository<TalentRequestStatus, UUID> {
  Optional<TalentRequestStatus> findByTalentRequestStatusNameAndIsActive(String name, Boolean isActive);
}

