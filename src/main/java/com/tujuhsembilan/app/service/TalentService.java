package com.tujuhsembilan.app.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tujuhsembilan.app.dto.request.PageRequest;
import com.tujuhsembilan.app.dto.request.TalentSpesificationRequest;
import com.tujuhsembilan.app.dto.response.talent_response.PositionResponse;
import com.tujuhsembilan.app.dto.response.talent_response.SkillsetResponse;
import com.tujuhsembilan.app.dto.response.talent_response.TalentDetailResponse;
import com.tujuhsembilan.app.dto.response.talent_response.TalentMessageResponseDto;
import com.tujuhsembilan.app.dto.response.talent_response.TalentResponse;
import com.tujuhsembilan.app.exception.NotFoundException;
import com.tujuhsembilan.app.model.talent.Talent;
import com.tujuhsembilan.app.repository.TalentRepository;
import com.tujuhsembilan.app.repository.approval_talent_repo.TalentRequestRepository;
import com.tujuhsembilan.app.service.specifications.TalentSpecification;

import lib.minio.MinioSrvc;

@Service
@Transactional
public class TalentService<NotFoundResponse> {
        @Autowired
        private TalentRepository talentRepository;

        @Autowired
        private TalentRequestRepository talentRequestRepository;

        @Autowired
        private MinioSrvc minioSrvc;

        @Autowired
        private MessageSource messageSource;
        // ============================GET ALL TALENT===============================//

        public ResponseEntity<TalentMessageResponseDto> getAllTalents(TalentSpesificationRequest filter,
                        PageRequest pageRequest) {
                Pageable pageable = pageRequest.getPage();
                Specification<Talent> spec = TalentSpecification.filter(filter);
                Page<Talent> talents = talentRepository.findAll(spec, pageable);
                long totalTalent = talentRepository.count();
                List<TalentResponse> talentResponseDTOs = talents.getContent().stream().map(
                                talent -> {
                                        TalentResponse response = new TalentResponse();
                                        response.setTalentId(talent.getTalentId());

                                        // Pastikan filename tidak null
                                        String filename = talent.getTalentPhotoFilename();
                                        if (filename != null && !filename.trim().isEmpty()) {
                                                response.setTalentPhotoUrl(minioSrvc.getPublicLink(filename));
                                        } else {
                                                response.setTalentPhotoUrl(null); // atau URL default
                                        }

                                        response.setTalentStatus(
                                                        talent.getTalentStatus() != null &&
                                                                        talent.getTalentStatus()
                                                                                        .getTalentStatusName() != null
                                                                        &&
                                                                        !talent.getTalentStatus().getTalentStatusName()
                                                                                        .trim().isEmpty()
                                                                                                        ? talent.getTalentStatus()
                                                                                                                        .getTalentStatusName()
                                                                                                        : null);

                                        response.setEmployeeStatus(
                                                        talent.getEmployeeStatus() != null &&
                                                                        talent.getEmployeeStatus()
                                                                                        .getEmployeeStatusName() != null
                                                                        &&
                                                                        !talent.getEmployeeStatus()
                                                                                        .getEmployeeStatusName().trim()
                                                                                        .isEmpty()
                                                                                                        ? talent.getEmployeeStatus()
                                                                                                                        .getEmployeeStatusName()
                                                                                                        : null);

                                        response.setEmployeeStatus(
                                                        talent.getEmployeeStatus() != null &&
                                                                        talent.getEmployeeStatus()
                                                                                        .getEmployeeStatusName() != null
                                                                        &&
                                                                        !talent.getEmployeeStatus()
                                                                                        .getEmployeeStatusName().trim()
                                                                                        .isEmpty()
                                                                                                        ? talent.getEmployeeStatus()
                                                                                                                        .getEmployeeStatusName()
                                                                                                        : null);

                                        response.setTalentAvailability(
                                                        talent.getTalentAvailability() != null
                                                                        ? talent.getTalentAvailability()
                                                                        : null);

                                        response.setTalentExperience(
                                                        talent.getExperience() != null ? talent.getExperience() : null);

                                        response.setTalentName(
                                                        talent.getTalentName() != null ? talent.getTalentName() : null);

                                        response.setTalentLevel(
                                                        talent.getTalentLevel() != null &&
                                                                        talent.getTalentLevel()
                                                                                        .getTalentLevelName() != null
                                                                        &&
                                                                        !talent.getTalentLevel().getTalentLevelName()
                                                                                        .trim().isEmpty()
                                                                                                        ? talent.getTalentLevel()
                                                                                                                        .getTalentLevelName()
                                                                                                        : null);

                                        // Mendapatkan PositionResponseDTO
                                        // List<PositionResponse> positions =
                                        // talentRepository.findPositionsByTalentId(talent.getTalentId());
                                        // response.setPosition(positions.isEmpty() ? null : positions);

                                        // // Mendapatkan SkillsetResponseDTO
                                        // List<SkillsetResponse> skillsets =
                                        // talentRepository.findSkillsetsByTalentId(talent.getTalentId());
                                        // response.setSkillSet(skillsets.isEmpty() ? null : skillsets);
                                        List<PositionResponse> positions = talent.getTalentPositions().stream()
                                                        .map(position -> new PositionResponse(
                                                                        position.getPosition().getPositionId(),
                                                                        position.getPosition().getPositionName()))
                                                        .collect(Collectors.toList());
                                        response.setPosition(positions.isEmpty() ? null : positions);

                                        List<SkillsetResponse> skillsets = talent.getTalentSkillsets().stream()
                                                        .map(skillset -> new SkillsetResponse(
                                                                        skillset.getSkillset().getSkillsetId(),
                                                                        skillset.getSkillset().getSkillsetName()))
                                                        .collect(Collectors.toList());
                                        response.setSkillSet(skillsets.isEmpty() ? null : skillsets);

                                        return response;
                                }).collect(Collectors.toList());

                TalentMessageResponseDto result = TalentMessageResponseDto.builder()
                                .data(talentResponseDTOs)
                                .totalData(totalTalent)
                                .message("Get All Talent Success")
                                .status("SUCCESS")
                                .statusCode(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.status(HttpStatus.OK).body(result);
        }

        // ===========================DETAIL TALENT=================================//
        public ResponseEntity<?> getTalentDetailById(UUID talentId) {
                // Fetch talent from the repository
                Optional<Talent> talentDetailOptional = talentRepository.findById(talentId);

                // Check if the talent exists
                if (talentDetailOptional.isEmpty()) {
                        String message = messageSource.getMessage("talent.not.found", null, Locale.getDefault());
                        String formatMessage = MessageFormat.format(message, talentId);

                        return ResponseEntity
                                        .status(HttpStatus.NOT_FOUND)
                                        .body(new NotFoundException(formatMessage, HttpStatus.NOT_FOUND.value(),
                                                        HttpStatus.NOT_FOUND.getReasonPhrase()));
                }

                // Retrieve the Talent entity
                Talent talent = talentDetailOptional.get();

                TalentDetailResponse talentDetailResponse = new TalentDetailResponse();
                talentDetailResponse.setTalentId(talent.getTalentId());
                talentDetailResponse.setTalentPhotoUrl(talent.getTalentPhotoUrl());
                talentDetailResponse.setTalentName(talent.getTalentName());
                talentDetailResponse.setTalentStatus(
                                talentDetailOptional.get().getTalentStatus() != null
                                                ? talent.getTalentStatus().getTalentStatusName()
                                                : null);
                talentDetailResponse.setNip(talent.getEmployeeNumber());
                talentDetailResponse.setSex(talent.getGender());
                talentDetailResponse.setDob(talent.getBirthDate());
                talentDetailResponse.setTalentDescription(talent.getTalentDescription());
                talentDetailResponse.setCv(talent.getTalentCvUrl());
                talentDetailResponse.setTalentExperience(talent.getExperience());
                talentDetailResponse.setTalentLevel(
                                talent.getTalentLevel() != null ? talent.getTalentLevel().getTalentLevelName() : null);
                talentDetailResponse.setProjectCompleted(talent.getTalentMetadatas() != null
                                ? talent.getTalentMetadatas().getTotalProjectCompleted()
                                : null);
                talentDetailResponse.setTotalRequested(talentRequestRepository.countRequestsByTalentId(talentId));
                talentDetailResponse.setEmail(talent.getEmail());
                talentDetailResponse.setCellphone(talent.getCellphone());
                talentDetailResponse.setEmployeeStatus(
                                talentDetailResponse.getEmployeeStatus() != null
                                                ? talent.getEmployeeStatus().getEmployeeStatusName()
                                                : null);
                talentDetailResponse.setTalentAvailability(talent.getTalentAvailability());

                // Set positions
                List<PositionResponse> positions = talent.getTalentPositions().stream()
                                .map(tp -> new PositionResponse(tp.getPosition().getPositionId(),
                                                tp.getPosition().getPositionName()))
                                .collect(Collectors.toList());
                talentDetailResponse.setPosition(positions.isEmpty() ? null : positions);

                // Set skill sets
                List<SkillsetResponse> skillsets = talent.getTalentSkillsets().stream()
                                .map(ts -> new SkillsetResponse(ts.getSkillset().getSkillsetId(),
                                                ts.getSkillset().getSkillsetName()))
                                .collect(Collectors.toList());
                talentDetailResponse.setSkillSet(skillsets.isEmpty() ? null : skillsets);

                // Set custom URL for talent photo if filename exists
                if (talent.getTalentPhotoFilename() != null) {
                        talentDetailResponse
                                        .setTalentPhotoUrl(minioSrvc.getPublicLink(talent.getTalentPhotoFilename()));
                }

                // Return the response with OK status
                return ResponseEntity.ok(talentDetailResponse);
        }
        // =======================mapping skillset & position ======================//
        // private List<PositionResponse> mapPositions(String positionIds, String
        // positionNames) {
        // String[] ids = positionIds.split(",");
        // String[] names = positionNames.split(",");

        // List<PositionResponse> positionResponseDtos = IntStream.range(0, ids.length)
        // .mapToObj(i -> new PositionResponse(UUID.fromString(ids[i].trim()),
        // names[i].trim()))
        // .collect(Collectors.toList());

        // return positionResponseDtos.stream()
        // .map(positionDto -> modelMapper.map(positionDto, PositionResponse.class))
        // .collect(Collectors.toList());
        // }

        // private List<SkillsetResponse> mapSkillsets(String skillIds, String
        // skillNames) {
        // String[] ids = skillIds.split(",");
        // String[] names = skillNames.split(",");

        // List<SkillsetResponse> skillsetResponseDtos = IntStream.range(0, ids.length)
        // .mapToObj(i -> new SkillsetResponse(UUID.fromString(ids[i].trim()),
        // names[i].trim()))
        // .collect(Collectors.toList());

        // return skillsetResponseDtos.stream()
        // .map(skillsetDto -> modelMapper.map(skillsetDto, SkillsetResponse.class))
        // .collect(Collectors.toList());
        // }
}
