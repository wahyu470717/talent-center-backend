package com.tujuhsembilan.app.service.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.tujuhsembilan.app.dto.request.TalentRequestDto;
import com.tujuhsembilan.app.model.talent.Talent;

import jakarta.persistence.criteria.Predicate;

public class TalentSpecification {
    public static Specification<Talent> talentFilter(TalentRequestDto talentRequestDto) {
        return (root, query, criteriaBuilder) -> {

            // Join<Talent, TalentPosition> talentPositionJoin = root.join("talentPositions");
            // Join<TalentPosition, Position> positionJoin = talentPositionJoin.join("position");
            List<Predicate> predicates = new ArrayList<>();

            if (talentRequestDto.getTalentName() != null) {
                String talentNameValue = "%" + talentRequestDto.getTalentName().toLowerCase() + "%";
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("talentName")), talentNameValue));
            }

            if (talentRequestDto.getTalentStatusId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("talentStatus"), talentRequestDto.getTalentStatusId()));
            }

            if (talentRequestDto.getEmployeeStatusId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("employeeStatus"), talentRequestDto.getEmployeeStatusId()));
            }

            if (talentRequestDto.getTalentAvailability() != null) {
                predicates.add(criteriaBuilder.equal(root.get("talentAvailability"), talentRequestDto.getTalentAvailability()));
            }

            if (talentRequestDto.getTalentExperience() != null) {
                predicates.add(criteriaBuilder.equal(root.get("experience"), talentRequestDto.getTalentExperience()));
            }

            if (talentRequestDto.getTalentLevelId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("talentLevel"), talentRequestDto.getTalentLevelId()));
            }

            if (talentRequestDto.getPositionIds() != null) {
                predicates.add(criteriaBuilder.equal(root.get("position"), talentRequestDto.getPositionIds()));
            }

            if (talentRequestDto.getSkillSetIds() != null) {
                predicates.add(criteriaBuilder.equal(root.get("skillSet"), talentRequestDto.getSkillSetIds()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
