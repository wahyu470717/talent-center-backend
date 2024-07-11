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
            List<Predicate> predicates = new ArrayList<>();

            if (talentRequestDto.getTalentId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("talentId"), talentRequestDto.getTalentId()));
            }

            if (talentRequestDto.getTalentName() != null) {
                String talentNameValue = "%" + talentRequestDto.getTalentName().toLowerCase() + "%";
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("talentName")), talentNameValue));
            }

            if (talentRequestDto.getTalentStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("talentStatus"), talentRequestDto.getTalentStatus()));
            }

            if (talentRequestDto.getEmployeeStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("employeeStatus"), talentRequestDto.getEmployeeStatus()));
            }

            if (talentRequestDto.getTalentAvailability() != null) {
                predicates.add(criteriaBuilder.equal(root.get("talentAvailability"), talentRequestDto.getTalentAvailability()));
            }

            if (talentRequestDto.getTalentExperience() != null) {
                predicates.add(criteriaBuilder.equal(root.get("experience"), talentRequestDto.getTalentExperience()));
            }

            if (talentRequestDto.getTalentLevel() != null) {
                predicates.add(criteriaBuilder.equal(root.get("talentLevel"), talentRequestDto.getTalentLevel()));
            }

            if (talentRequestDto.getPosition() != null) {
                predicates.add(criteriaBuilder.equal(root.get("position"), talentRequestDto.getPosition()));
            }

            if (talentRequestDto.getSkillSet() != null) {
                predicates.add(criteriaBuilder.equal(root.get("skillSet"), talentRequestDto.getSkillSet()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
