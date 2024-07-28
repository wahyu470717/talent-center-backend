package com.tujuhsembilan.app.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tujuhsembilan.app.model.talent.Talent;
import com.tujuhsembilan.app.dto.response.talent_response.*;

public interface TalentRepository extends JpaRepository<Talent, UUID>, JpaSpecificationExecutor<Talent> {

  // @Query("SELECT t, " +
  // "STRING_AGG(CAST(p.positionId AS text), ',') AS positionIds, " +
  // "STRING_AGG(p.positionName, ',') AS positionNames, " +
  // "STRING_AGG(CAST(s.skillsetId AS text), ',') AS skillIds, " +
  // "STRING_AGG(s.skillsetName, ',') AS skillNames " +
  // "FROM Talent t " +
  // "LEFT JOIN t.talentPositions tp " +
  // "LEFT JOIN tp.position p " +
  // "LEFT JOIN t.talentSkillsets ts " +
  // "LEFT JOIN ts.skillset s " +
  // "GROUP BY t.talentId, t.talentName, t.talentPhotoFilename, t.employeeNumber,
  // t.gender, t.birthDate, t.talentDescription, t.talentCvFilename, t.experience,
  // t.email, t.cellphone, t.biographyVideoUrl, t.isAddToListEnable,
  // t.talentAvailability, t.isActive, t.createdBy, t.createdTime,
  // t.lastModifiedBy, t.lastModifiedTime, t.sex, t.talentCvUrl, t.talentPhotoUrl,
  // t.totalProjectCompleted, t.talentStatus, t.employeeStatus, t.talentLevel")
  // @EntityGraph(value = "Talent.positionsAndSkills", type =
  // EntityGraphType.LOAD)
  // Page<Object[]> findAllTalentsWithPositionsAndSkills(Pageable pageable);

  @Query("SELECT new com.tujuhsembilan.app.dto.response.talent_response.PositionResponse(p.positionId, p.positionName) "
      +
      "FROM Talent t " +
      "JOIN t.talentPositions tp " +
      "JOIN tp.position p " +
      "WHERE t.talentId = :talentId")
  List<PositionResponse> findPositionsByTalentId(@Param("talentId") UUID talentId);

  @Query("SELECT new com.tujuhsembilan.app.dto.response.talent_response.SkillsetResponse(s.skillsetId, s.skillsetName) "
      +
      "FROM Talent t " +
      "JOIN t.talentSkillsets ts " +
      "JOIN ts.skillset s " +
      "WHERE t.talentId = :talentId")
  List<SkillsetResponse> findSkillsetsByTalentId(@Param("talentId") UUID talentId);

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
