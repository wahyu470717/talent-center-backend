package com.tujuhsembilan.app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tujuhsembilan.app.dto.request.PageRequest;
import com.tujuhsembilan.app.dto.request.TalentRequestDto;
import com.tujuhsembilan.app.dto.response.TalentDto;
import com.tujuhsembilan.app.dto.response.TalentResponse;
import com.tujuhsembilan.app.model.talent.Talent;
import com.tujuhsembilan.app.repository.TalentRepository;
import com.tujuhsembilan.app.service.specifications.TalentSpecification;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class TalentService {
    @Autowired
    private TalentRepository talentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<TalentResponse> getAllTalents(TalentRequestDto filter, PageRequest pageRequest) {
        Pageable pageable = pageRequest.getPage();
        Page<Talent> talentPage = talentRepository.findAll(TalentSpecification.talentFilter(filter), pageable);

        TalentResponse talentResponse = new TalentResponse();
        talentResponse.setData(talentPage.getContent().stream()
            .map(talent -> modelMapper.map(talent, TalentDto.class))
            .collect(Collectors.toList()));
        talentResponse.setPageNumber(talentPage.getNumber());
        talentResponse.setPageSize(talentPage.getSize());
        talentResponse.setTotalElements(talentPage.getTotalElements());
        talentResponse.setTotalPages(talentPage.getTotalPages());

        return ResponseEntity.ok(talentResponse);
    }
}
