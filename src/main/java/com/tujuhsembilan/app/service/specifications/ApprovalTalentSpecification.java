package com.tujuhsembilan.app.service.specifications;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.tujuhsembilan.app.dto.request.ApprovalRequestDto;
import com.tujuhsembilan.app.model.client.Client;
import com.tujuhsembilan.app.model.talent.Talent;
import com.tujuhsembilan.app.model.talent.TalentLevel;
import com.tujuhsembilan.app.model.talent.talent_request.TalentRequest;
import com.tujuhsembilan.app.model.talent.talent_request.TalentRequestStatus;
import com.tujuhsembilan.app.model.talent.talent_request.TalentWishlist;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

public class ApprovalTalentSpecification {
    public static Specification<TalentRequest> talentFilter(ApprovalRequestDto approvalRequestDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


            // Filter berdasarkan status
            if (approvalRequestDto.getStatus() != null && !approvalRequestDto.getStatus().isEmpty()) {
                Join<TalentRequest, TalentRequestStatus> statusJoin = root.join("talentRequestStatus");
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(statusJoin.get("talentRequestStatusName")),
                    "%" + approvalRequestDto.getStatus().toLowerCase() + "%"));
            }

            // Filter berdasarkan nama talent dengan substring
            if (approvalRequestDto.getTalent() != null && !approvalRequestDto.getTalent().isEmpty()) {
                Join<TalentRequest, TalentWishlist> wishlistJoin = root.join("talentWishlist");
                Join<TalentWishlist, Talent> talentJoin = wishlistJoin.join("talent");
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(talentJoin.get("talentName")),
                    "%" + approvalRequestDto.getTalent().toLowerCase() + "%"));
            }

            // Filter berdasarkan instansi (atau relasi dengan entitas lain jika perlu)
            if (approvalRequestDto.getInstansi() != null && !approvalRequestDto.getInstansi().isEmpty()) {
                Join<TalentRequest, TalentWishlist> wishlistJoin = root.join("talentWishlist");
                Join<TalentWishlist, Client> clientJoin = wishlistJoin.join("client");
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(clientJoin.get("agencyName")),
                    "%" + approvalRequestDto.getInstansi().toLowerCase() + "%"));
            }

            // Filter berdasarkan tanggal request
            if (approvalRequestDto.getRequestDate() != null) {
                String dateRequest = approvalRequestDto.getRequestDate();

                predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.function("TO_CHAR", String.class,
                        root.get("requestDate"),
                        criteriaBuilder.literal("YYYY-MM-DD")),
                    criteriaBuilder.literal(dateRequest.toString().substring(0, 10))
                ));
            }


            // Kembalikan hasil filter sebagai Predicate
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
