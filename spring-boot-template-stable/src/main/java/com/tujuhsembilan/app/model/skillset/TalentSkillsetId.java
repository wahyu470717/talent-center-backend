package com.tujuhsembilan.app.model.skillset;

import java.io.Serializable;
import java.util.UUID;

import com.tujuhsembilan.app.model.talent.Talent;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TalentSkillsetId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "talent_id", referencedColumnName = "talent_id")
    private Talent talentId;

    @ManyToOne
    @JoinColumn(name = "skillset_id", referencedColumnName = "skillset_id")
    private Skillset skillsetId;
}
