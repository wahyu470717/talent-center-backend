package com.tujuhsembilan.app.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.tujuhsembilan.app.model.talent.Talent;

public interface TalentRepository extends JpaRepository<Talent, UUID>, JpaSpecificationExecutor<Talent> {

  @Query("SELECT t, " +
    "STRING_AGG(CAST(p.positionId AS text), ',') AS positionIds, " +
    "STRING_AGG(p.positionName, ',') AS positionNames, " +
    "STRING_AGG(CAST(s.skillsetId AS text), ',') AS skillIds, " +
    "STRING_AGG(s.skillsetName, ',') AS skillNames " +
    "FROM Talent t " +
    "JOIN t.talentPositions tp " +
    "JOIN tp.position p " +
    "JOIN t.talentSkillsets ts " +
    "JOIN ts.skillset s " +
    "GROUP BY t")
  Page<Object[]> findAllTalentsWithPositionsAndSkills(Pageable pageable);

  @Query("SELECT t, " +
  "STRING_AGG(CAST(p.positionId AS text), ',') AS positionIds, " +
  "STRING_AGG(p.positionName, ',') AS positionNames, " +
  "STRING_AGG(CAST(s.skillsetId AS text), ',') AS skillIds, " +
  "STRING_AGG(s.skillsetName, ',') AS skillNames " +
  "FROM Talent t " +
  "LEFT JOIN t.talentPositions tp " +
  "LEFT JOIN tp.position p " +
  "LEFT JOIN t.talentSkillsets ts " +
  "LEFT JOIN ts.skillset s " +
  "WHERE t.talentId = :talentId " +
  "GROUP BY t.talentId")
Optional<Object> findTalentWithPositionsAndSkillsById(UUID talentId);
}
