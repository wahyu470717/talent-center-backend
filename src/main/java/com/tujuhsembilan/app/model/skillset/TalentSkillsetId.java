package com.tujuhsembilan.app.model.skillset;

import java.io.Serializable;
import java.util.UUID;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EntityListeners(AuditingEntityListener.class)
public class TalentSkillsetId implements Serializable {

    @Column(name = "talent_id")
    private UUID talentId;

    @Column(name = "skillset_id")
    private UUID skillsetId;
}
