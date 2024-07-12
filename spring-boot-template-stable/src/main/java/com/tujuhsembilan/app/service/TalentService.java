package com.tujuhsembilan.app.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tujuhsembilan.app.dto.request.PageRequest;
import com.tujuhsembilan.app.dto.request.TalentRequestDto;
import com.tujuhsembilan.app.dto.response.TalentDetailResponse;
import com.tujuhsembilan.app.dto.response.TalentDto;
import com.tujuhsembilan.app.dto.response.TalentResponse;
import com.tujuhsembilan.app.model.talent.Talent;
import com.tujuhsembilan.app.repository.TalentRepository;
import com.tujuhsembilan.app.service.specifications.TalentSpecification;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class TalentService {
    @Autowired
    private TalentRepository talentRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private DateTimeFormatter dateFormatter;

    @PostConstruct
    private void configureModelMapper() {
        // Define explicit mappings to resolve conflicts
        modelMapper.addMappings(new PropertyMap<Talent, TalentDetailResponse>() {
            @Override
            protected void configure() {
                map().setTalentPhoto(source.getTalentPhotoUrl());
                map().setCv(source.getTalentCvUrl());
                map().setNip(source.getEmployeeNumber());
                using(ctx -> ((Talent) ctx.getSource())
                    .getBirthDate().toLocalDateTime().toLocalDate().format(dateFormatter)).map(source, destination.getDob());

            }
        });
    }

    public ResponseEntity<TalentResponse> getAllTalents(TalentRequestDto filter, PageRequest pageRequest) {
        Pageable pageable = pageRequest.getPage();
    
        // Menggunakan specification untuk filter berdasarkan kriteria dari `filter`
        Page<Talent> talentPage = talentRepository.findAll(TalentSpecification.talentFilter(filter), pageable);
    
        // Mapping hasil dari entity `Talent` ke DTO `TalentDto`
        TalentResponse talentResponse = new TalentResponse();
        talentResponse.setData(talentPage.getContent().stream()
            .map(talent -> modelMapper.map(talent, TalentDto.class))
            .collect(Collectors.toList()));
        // talentResponse.setPageNumber(talentPage.getNumber());
        // talentResponse.setPageSize(talentPage.getSize());
        // talentResponse.setTotalElements(talentPage.getTotalElements());
        // talentResponse.setTotalPages(talentPage.getTotalPages());
    
        return ResponseEntity.ok(talentResponse);
    }

    //===========================Detail Talent=================================
    public ResponseEntity<TalentDetailResponse> getTalentById(UUID talentId) {

        Optional<Talent> talentOptional = talentRepository.findById(talentId);

        if (talentOptional.isPresent()) {
            Talent talent = talentOptional.get();
            TalentDetailResponse response = modelMapper.map(talent, TalentDetailResponse.class);
            // response.setPositions(talent.getTalentPositions().stream()
            //     .map(tp -> modelMapper.map(tp.getPosition(), PositionResponseDto.class))
            //     .collect(Collectors.toList()));
            // response.setSkillSets(talent.getTalentSkillsets().stream()
            //     .map(ts -> modelMapper.map(ts.getSkillset(), SkillsetResponseDto.class))
            //     .collect(Collectors.toList()));
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
