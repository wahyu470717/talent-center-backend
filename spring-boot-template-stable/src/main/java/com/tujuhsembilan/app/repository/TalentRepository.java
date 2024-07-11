package com.tujuhsembilan.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tujuhsembilan.app.model.talent.Talent;

public interface TalentRepository extends JpaRepository<Talent, UUID>, JpaSpecificationExecutor<Talent> {
  
}
