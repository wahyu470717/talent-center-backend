package com.tujuhsembilan.app.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
public class CreateTalentService {
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

    public MessageResponse createTalent(TalentRequestDto talentRequest, MultipartFile fotoFile, MultipartFile cvFile) {
        try {
            // Create a new Talent
            Talent talent = new Talent();
            talent.setTalentName(talentRequest.getTalentName());

            // Fetch and set TalentStatus
            Optional<TalentStatus> talentStatusOptional = talentStatusRepository
                    .findById(talentRequest.getTalentStatusId());
            if (talentStatusOptional.isPresent()) {
                talent.setTalentStatus(talentStatusOptional.get());
            } else {
                return new MessageResponse(0, "Invalid TalentStatus ID", 400, "ERROR");
            }

            // Fetch and set EmployeeStatus
            Optional<EmployeeStatus> employeeStatusOptional = employeeStatusRepository
                    .findById(talentRequest.getEmployeeStatusId());
            if (employeeStatusOptional.isPresent()) {
                talent.setEmployeeStatus(employeeStatusOptional.get());
            } else {
                return new MessageResponse(0, "Invalid EmployeeStatus ID", 400, "ERROR");
            }

            // Fetch and set TalentLevel
            Optional<TalentLevel> talentLevelOptional = talentLevelRepository
                    .findById(talentRequest.getTalentLevelId());
            if (talentLevelOptional.isPresent()) {
                talent.setTalentLevel(talentLevelOptional.get());
            } else {
                return new MessageResponse(0, "Invalid TalentLevel ID", 400, "ERROR");
            }

            // Handle file upload to MinIO
            if (fotoFile != null && !fotoFile.isEmpty()) {
                String photoFilename = minioService.uploadFileToMinio(talentRequest, fotoFile);
                String photoUrl = minioService.getPublicLink(photoFilename);
                talent.setTalentPhotoUrl(photoUrl);
                talent.setTalentPhotoFilename(photoFilename);
            }

            if (cvFile != null && !cvFile.isEmpty()) {
                String cvFilename = minioService.uploadFileToMinio(talentRequest, cvFile);
                String cvUrl = minioService.getPublicLink(cvFilename);
                talent.setTalentCvUrl(cvUrl);
            }

            // Set remaining fields
            talent.setEmployeeNumber(talentRequest.getNip());
            talent.setGender(talentRequest.getGender());
            talent.setBirthDate(talentRequest.getDob());
            talent.setTalentDescription(talentRequest.getTalentDescription());
            talent.setTalentAvailability(talentRequest.getTalentAvailability());
            talent.setExperience(talentRequest.getTalentExperience());
            talent.setTotalProjectCompleted(talentRequest.getProjectCompleted());
            talent.setEmail(talentRequest.getEmail());
            talent.setCellphone(talentRequest.getCellphone());

            // Save Talent first to get the ID
            talent = talentRepository.save(talent);

            // Initialize collections
            talent.setTalentPositions(new ArrayList<>());
            talent.setTalentSkillsets(new ArrayList<>());

            // Fetch and set TalentPositions
            if (talentRequest.getPositionIds() != null) {
                UUID talentId = talent.getTalentId(); // Final reference
                List<TalentPosition> talentPositions = new ArrayList<>();
                for (var positionIdDto : talentRequest.getPositionIds()) {
                    UUID positionId = positionIdDto.getPositionId();
                    TalentPositionId talentPositionId = new TalentPositionId(talentId, positionId);
                    TalentPosition talentPosition = new TalentPosition();
                    talentPosition.setId(talentPositionId);
                    talentPosition.setTalent(talent);
                    Position position = positionRepository.findById(positionId)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid Position ID"));
                    talentPosition.setPosition(position);
                    talentPositions.add(talentPosition);
                }
                // Set positions to talent
                talent.getTalentPositions().addAll(talentPositions);
            }

            // Fetch and set TalentSkillsets
            if (talentRequest.getSkillSetIds() != null) {
                UUID talentId = talent.getTalentId(); // Final reference
                List<TalentSkillset> talentSkillsets = new ArrayList<>();
                for (var skillsetIdDto : talentRequest.getSkillSetIds()) {
                    UUID skillsetId = skillsetIdDto.getSkillId();
                    TalentSkillsetId talentSkillsetId = new TalentSkillsetId(talentId, skillsetId);
                    TalentSkillset talentSkillset = new TalentSkillset();
                    talentSkillset.setId(talentSkillsetId);
                    talentSkillset.setTalent(talent);

                    Skillset skillset = skillsetRepository.findById(skillsetId)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid Skillset ID"));

                    talentSkillset.setSkillset(skillset);

                    talentSkillsets.add(talentSkillset);
                }
                // Set skillsets to talent
                talent.getTalentSkillsets().addAll(talentSkillsets);
            }

            // Save the updated Talent with positions and skillsets
            talentRepository.save(talent);

            return new MessageResponse(1, "Talent created successfully", 200, "SUCCESS");
        } catch (IllegalArgumentException e) {
            return new MessageResponse(0, String.format("Failed to create talent: %s", e.getMessage()), 400, "ERROR");
        } catch (IOException e) {
            return new MessageResponse(0, String.format("Failed to upload file: %s", e.getMessage()), 500, "ERROR");
        } catch (Exception e) {
            return new MessageResponse(0, String.format("An unexpected error occurred: %s", e.getMessage()), 500, "ERROR");
        }
    }
}
