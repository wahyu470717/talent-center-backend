package com.tujuhsembilan.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.tujuhsembilan.app.model.skillset.Skillset;

public interface SkillsetRepository extends JpaRepository<Skillset, UUID>, JpaSpecificationExecutor<Skillset> {
    @Query("SELECT p FROM Skillset p WHERE p.skillsetName = :skillsetName")
    Optional<Skillset> findSkillsetBySkillsetName(String skillsetName);
}