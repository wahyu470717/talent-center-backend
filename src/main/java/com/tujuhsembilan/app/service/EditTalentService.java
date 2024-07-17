package com.tujuhsembilan.app.service;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tujuhsembilan.app.dto.request.TalentRequestDto;
import com.tujuhsembilan.app.dto.response.talent_response.MessageResponse;
import com.tujuhsembilan.app.model.employee.EmployeeStatus;
import com.tujuhsembilan.app.model.position.Position;
import com.tujuhsembilan.app.model.position.TalentPosition;
import com.tujuhsembilan.app.model.position.TalentPositionId;
import com.tujuhsembilan.app.model.skillset.Skillset;
import com.tujuhsembilan.app.model.skillset.TalentSkillset;
import com.tujuhsembilan.app.model.skillset.TalentSkillsetId;
import com.tujuhsembilan.app.model.talent.Talent;
import com.tujuhsembilan.app.model.talent.TalentLevel;
import com.tujuhsembilan.app.model.talent.TalentStatus;
import com.tujuhsembilan.app.repository.EmployeeStatusRepository;
import com.tujuhsembilan.app.repository.PositionRepository;
import com.tujuhsembilan.app.repository.SkillsetRepository;
import com.tujuhsembilan.app.repository.TalentLevelRepository;
import com.tujuhsembilan.app.repository.TalentRepository;
import com.tujuhsembilan.app.repository.TalentStatusRepository;

import lib.minio.MinioSrvc;

@Service
@Transactional
public class EditTalentService {

  @Autowired
  private TalentRepository talentRepository;

  @Autowired
  private TalentStatusRepository talentStatusRepository;

  @Autowired
  private EmployeeStatusRepository employeeStatusRepository;

  @Autowired
  private TalentLevelRepository talentLevelRepository;

  @Autowired
  private PositionRepository positionRepository;

  @Autowired
  private SkillsetRepository skillsetRepository;

  @Autowired
  private MinioSrvc minioService;

  public MessageResponse editTalent(UUID talentId, TalentRequestDto talentRequest, MultipartFile talentFile) {
    try {
      Optional<Talent> talentOptional = talentRepository.findById(talentId);
      if (talentOptional.isPresent()) {
        Talent talent = talentOptional.get();

        // Update talent information
        talent.setTalentName(talentRequest.getTalentName());
        talent.setEmployeeNumber(talentRequest.getNip());
        talent.setSex(talentRequest.getSex());
        talent.setBirthDate(talentRequest.getDob());
        talent.setTalentDescription(talentRequest.getTalentDescription());
        talent.setTalentAvailability(talentRequest.getTalentAvailability());
        talent.setExperience(talentRequest.getTalentExperience());
        talent.setTotalProjectCompleted(talentRequest.getProjectCompleted());
        talent.setEmail(talentRequest.getEmail());
        talent.setCellphone(talentRequest.getCellphone());

        // Fetch and set TalentStatus
        Optional<TalentStatus> talentStatusOptional = talentStatusRepository
            .findById(talentRequest.getTalentStatusId());
        if (talentStatusOptional.isPresent()) {
          talent.setTalentStatus(talentStatusOptional.get());
        } else {
          throw new IllegalArgumentException("Invalid TalentStatus ID");
        }

        // Fetch and set EmployeeStatus
        Optional<EmployeeStatus> employeeStatusOptional = employeeStatusRepository
            .findById(talentRequest.getEmployeeStatusId());
        if (employeeStatusOptional.isPresent()) {
          talent.setEmployeeStatus(employeeStatusOptional.get());
        } else {
          throw new IllegalArgumentException("Invalid EmployeeStatus ID");
        }

        // Fetch and set TalentLevel
        Optional<TalentLevel> talentLevelOptional = talentLevelRepository.findById(talentRequest.getTalentLevelId());
        if (talentLevelOptional.isPresent()) {
          talent.setTalentLevel(talentLevelOptional.get());
        } else {
          throw new IllegalArgumentException("Invalid TalentLevel ID");
        }

        // Update TalentPositions
        Set<TalentPosition> updatedPositions = talentRequest.getPositionIds().stream().map(positionId -> {
          TalentPositionId talentPositionId = new TalentPositionId(talent.getTalentId(), positionId);
          TalentPosition talentPosition = new TalentPosition();
          talentPosition.setId(talentPositionId);
          talentPosition.setTalent(talent);
          Position position = positionRepository.findById(positionId)
              .orElseThrow(() -> new IllegalArgumentException("Invalid Position ID"));
          talentPosition.setPosition(position);
          return talentPosition;
        }).collect(Collectors.toSet());
        talent.setTalentPositions(updatedPositions);

        // Update TalentSkillsets
        Set<TalentSkillset> updatedSkillsets = talentRequest.getSkillSetIds().stream().map(skillsetId -> {
          TalentSkillsetId talentSkillsetId = new TalentSkillsetId(talent.getTalentId(), skillsetId);
          TalentSkillset talentSkillset = new TalentSkillset();
          talentSkillset.setId(talentSkillsetId);
          talentSkillset.setTalent(talent);
          Skillset skillset = skillsetRepository.findById(skillsetId)
              .orElseThrow(() -> new IllegalArgumentException("Invalid Skillset ID"));
          talentSkillset.setSkillset(skillset);
          return talentSkillset;
        }).collect(Collectors.toSet());
        talent.setTalentSkillsets(updatedSkillsets);

        // Handle file upload to MinIO
        if (talentFile != null && !talentFile.isEmpty()) {
          String photoFilename = minioService.uploadFileToMinio(talentRequest, talentFile);
          talent.setTalentPhotoUrl(minioService.getPublicLink(photoFilename));

          String cvFilename = minioService.uploadFileToMinio(talentRequest, talentFile);
          talent.setTalentCvUrl(minioService.getPublicLink(cvFilename));
        }

        talentRepository.save(talent);
        return new MessageResponse("Talent updated successfully", 200, "SUCCESS");
      } else {
        return new MessageResponse("Talent not found with ID: " + talentId, 404, "ERROR");
      }
    } catch (IllegalArgumentException e) {
      return new MessageResponse("Failed to update talent: " + e.getMessage(), 400, "ERROR");
    } catch (IOException e) {
      return new MessageResponse("Failed to upload file: " + e.getMessage(), 500, "ERROR");
    } catch (Exception e) {
      return new MessageResponse("An unexpected error occurred: " + e.getMessage(), 500, "ERROR");
    }
  }
}
