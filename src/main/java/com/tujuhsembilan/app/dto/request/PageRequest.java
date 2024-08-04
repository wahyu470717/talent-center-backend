package com.tujuhsembilan.app.dto.request;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageRequest {
    private String sortBy;
    private Integer pageSize;
    private Integer pageNumber;
 public Pageable getPage() {
        int pageNumberValue = (pageNumber != null) ? pageNumber < 1 ? 1 : pageNumber : 1;
        int pageSizeValue = (pageSize != null) ? pageSize < 1 ? 1 : pageSize : 10;

        Sort sort = Sort.by(Direction.DESC, "experience") // Default sort
                .and(Sort.by(Direction.ASC, "talentLevel"));

        if (sortBy != null) {
            String[] parts = sortBy.split(",");
            if (parts.length == 2) {
                String sortField = parts[0];
                String sortOrder = parts[1];
                
                // Check if the sortField is valid
                if (sortField.equals("talentName")) {
                    sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
                } else {
                    // Default sorting if sortField is not recognized
                    sort = Sort.by(Direction.DESC, "experience")
                            .and(Sort.by(Direction.ASC, "talentLevel"));
                }
            }
        }

        return org.springframework.data.domain.PageRequest.of(pageNumberValue - 1, pageSizeValue, sort);
    }

    public Pageable getPageApproval() {
        int pageNumberValue = (pageNumber != null) ? pageNumber < 1 ? 1 : pageNumber : 1;
        int pageSizeValue = (pageSize != null) ? pageSize < 1 ? 1 : pageSize : 10;

        Sort sort = Sort.by(Direction.DESC, "requestDate"); // Default sort

        if (sortBy != null) {
            String[] parts = sortBy.split(",");
            if (parts.length == 2) {
                String sortField = parts[0];
                String sortOrder = parts[1];

                // Check if the sortField is valid
                switch (sortField) {
                    case "talentName":
                        sort = Sort.by(Sort.Direction.fromString(sortOrder), "talentWishlist.talent.talentName");
                        break;
                    case "agencyName":
                        sort = Sort.by(Sort.Direction.fromString(sortOrder), "talentWishlist.client.agencyName");
                        break;
                    case "requestDate":
                        sort = Sort.by(Sort.Direction.fromString(sortOrder), "requestDate");
                        break;
                    default:
                        sort = Sort.by(Direction.DESC, "requestDate"); // Default sorting
                        break;
                }
            }
        }

        return org.springframework.data.domain.PageRequest.of(pageNumberValue - 1, pageSizeValue, sort);
    }
    
}
