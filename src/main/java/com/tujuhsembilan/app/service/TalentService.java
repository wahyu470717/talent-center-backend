package com.tujuhsembilan.app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tujuhsembilan.app.dto.request.PageRequest;
import com.tujuhsembilan.app.dto.request.TalentRequestDto;
import com.tujuhsembilan.app.dto.response.talent_response.PositionResponse;
import com.tujuhsembilan.app.dto.response.talent_response.SkillsetResponse;
import com.tujuhsembilan.app.dto.response.talent_response.TalentDetailResponse;
import com.tujuhsembilan.app.dto.response.talent_response.TalentMessageResponseDto;
import com.tujuhsembilan.app.dto.response.talent_response.TalentResponse;
import com.tujuhsembilan.app.model.talent.Talent;
import com.tujuhsembilan.app.repository.TalentRepository;

import lib.minio.MinioSrvc;

@Service
@Transactional
public class TalentService {
    @Autowired
    private TalentRepository talentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MinioSrvc minioSrvc;

    // ============================GET ALL TALENT===============================//

    public ResponseEntity<TalentMessageResponseDto> getAllTalents(TalentRequestDto filter, PageRequest pageRequest) {
        Pageable pageable = pageRequest.getPage();

        Page<Talent> talents = talentRepository.findAll(pageable);
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

                    response.setTalentName(talent.getTalentName());
                    response.setTalentStatus(talent.getTalentStatus().getTalentStatusName());
                    response.setEmployeeStatus(talent.getEmployeeStatus().getEmployeeStatusName());
                    response.setTalentAvailability(talent.getTalentAvailability());
                    response.setTalentExperience(talent.getExperience());
                    response.setTalentLevel(talent.getTalentLevel().getTalentLevelName());

                    // Mendapatkan PositionResponseDTO
                    List<PositionResponse> positions = talentRepository.findPositionsByTalentId(talent.getTalentId());
                    response.setPosition(positions.isEmpty() ? null : positions);

                    // Mendapatkan SkillsetResponseDTO
                    List<SkillsetResponse> skillsets = talentRepository.findSkillsetsByTalentId(talent.getTalentId());
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
    public ResponseEntity<TalentDetailResponse> getTalentDetailById(UUID talentId) {
        Optional<Object> talentDataOptional = talentRepository.findTalentWithPositionsAndSkillsById(talentId);

        if (talentDataOptional.isPresent()) {
            Object talentData = talentDataOptional.get();
            if (talentData instanceof Object[]) {
                Object[] dataArray = (Object[]) talentData;
                TalentDetailResponse talentDetailResponse = mapToTalentDetailResponse(dataArray);
                return ResponseEntity.ok(talentDetailResponse);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private TalentDetailResponse mapToTalentDetailResponse(Object[] data) {
        Talent talent = (Talent) data[0];
        String positionIds = (String) data[1];
        String positionNames = (String) data[2];
        String skillIds = (String) data[3];
        String skillNames = (String) data[4];

        TalentDetailResponse talentDetailResponse = new TalentDetailResponse();

        // Explicitly map properties
        talentDetailResponse.setTalentId(talent.getTalentId());
        talentDetailResponse.setTalentPhotoUrl(talent.getTalentPhotoUrl());
        talentDetailResponse.setTalentName(talent.getTalentName());
        talentDetailResponse.setEmployeeStatus(talent.getEmployeeStatus().getEmployeeStatusName());
        talentDetailResponse.setNip(talent.getEmployeeNumber());
        talentDetailResponse.setSex(talent.getSex());
        talentDetailResponse.setTalentExperience(talent.getExperience());
        talentDetailResponse.setTalentLevel(talent.getTalentLevel().getTalentLevelName());
        talentDetailResponse.setTalentStatus(talent.getTalentStatus().getTalentStatusName());
        talentDetailResponse.setDob(talent.getBirthDate());
        talentDetailResponse.setTalentDescription(talent.getTalentDescription());
        talentDetailResponse.setCellphone(talent.getCellphone());
        talentDetailResponse.setEmail(talent.getEmail());
        talentDetailResponse.setProjectCompleted(talent.getTotalProjectCompleted());
        talentDetailResponse.setVideoUrl(talent.getBiographyVideoUrl());
        if (talent.getTalentCvFilename() != null) {
            talentDetailResponse.setCv(talent.getTalentCvFilename());
        } else if (talent.getTalentCvUrl() != null) {
            talentDetailResponse.setCv(talent.getTalentCvUrl());
        } else {
            talentDetailResponse.setCv(null); // or set default value as needed
        }

        talentDetailResponse.setPositions(mapPositions(positionIds, positionNames));
        talentDetailResponse.setSkillSets(mapSkillsets(skillIds, skillNames));

        return talentDetailResponse;
    }

    // =======================mapping skillset & position ======================//
    private List<PositionResponse> mapPositions(String positionIds, String positionNames) {
        String[] ids = positionIds.split(",");
        String[] names = positionNames.split(",");

        List<PositionResponse> positionResponseDtos = IntStream.range(0, ids.length)
                .mapToObj(i -> new PositionResponse(UUID.fromString(ids[i].trim()), names[i].trim()))
                .collect(Collectors.toList());

        return positionResponseDtos.stream()
                .map(positionDto -> modelMapper.map(positionDto, PositionResponse.class))
                .collect(Collectors.toList());
    }

    private List<SkillsetResponse> mapSkillsets(String skillIds, String skillNames) {
        String[] ids = skillIds.split(",");
        String[] names = skillNames.split(",");

        List<SkillsetResponse> skillsetResponseDtos = IntStream.range(0, ids.length)
                .mapToObj(i -> new SkillsetResponse(UUID.fromString(ids[i].trim()), names[i].trim()))
                .collect(Collectors.toList());

        return skillsetResponseDtos.stream()
                .map(skillsetDto -> modelMapper.map(skillsetDto, SkillsetResponse.class))
                .collect(Collectors.toList());
    }
}
