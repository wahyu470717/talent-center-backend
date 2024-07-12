package com.tujuhsembilan.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.tujuhsembilan.app.model.talent.Talent;

public interface TalentRepository extends JpaRepository<Talent, UUID>, JpaSpecificationExecutor<Talent> {
@EntityGraph(attributePaths = {"talentPositions.position", "talentSkillsets.skillset"})
    Optional<Talent> findById(UUID id);
}
