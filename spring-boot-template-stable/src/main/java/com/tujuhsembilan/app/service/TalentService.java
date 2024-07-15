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
import com.tujuhsembilan.app.dto.request.TalentRequest;
import com.tujuhsembilan.app.dto.response.talent_response.PositionResponse;
import com.tujuhsembilan.app.dto.response.talent_response.SkillsetResponse;
import com.tujuhsembilan.app.dto.response.talent_response.TalentDetailResponse;
import com.tujuhsembilan.app.dto.response.talent_response.TalentResponse;
import com.tujuhsembilan.app.model.talent.Talent;
import com.tujuhsembilan.app.repository.TalentRepository;

@Service
@Transactional
public class TalentService {
    @Autowired
    private TalentRepository talentRepository;

    @Autowired
    private ModelMapper modelMapper;

    // ============================GET ALL TALENT===============================//

    public ResponseEntity<List<TalentResponse>> getAllTalents(TalentRequest filter, PageRequest pageRequest) {
        Pageable pageable = pageRequest.getPage();
        Page<Object[]> talentData = talentRepository.findAllTalentsWithPositionsAndSkills(pageable);

        List<TalentResponse> talentResponses = talentData.getContent().stream()
                .map(this::mapToTalentResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(talentResponses);
    }

    private TalentResponse mapToTalentResponse(Object[] data) {
        Talent talent = (Talent) data[0];
        String positionIds = (String) data[1];
        String positionNames = (String) data[2];
        String skillIds = (String) data[3];
        String skillNames = (String) data[4];

        TalentResponse talentResponse = new TalentResponse();
        talentResponse.setTalentId(talent.getTalentId());
        talentResponse.setTalentPhotoUrl(talent.getTalentPhotoUrl());
        talentResponse.setTalentName(talent.getTalentName());
        talentResponse.setEmployeeStatus(talent.getEmployeeStatus().getEmployeeStatusName());
        talentResponse.setTalentAvailability(talent.getTalentAvailability());
        talentResponse.setTalentExperience(talent.getExperience());
        talentResponse.setTalentLevel(talent.getTalentLevel().getTalentLevelName());
        talentResponse.setTalentStatus(talent.getTalentStatus().getTalentStatusName());
        talentResponse.setPosition(mapPositions(positionIds, positionNames));
        talentResponse.setSkillSet(mapSkillsets(skillIds, skillNames));

        return talentResponse;
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
