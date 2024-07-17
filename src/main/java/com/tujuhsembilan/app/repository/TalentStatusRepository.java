package com.tujuhsembilan.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tujuhsembilan.app.model.talent.TalentStatus;

public interface TalentStatusRepository extends JpaRepository<TalentStatus, UUID> {
  
}
