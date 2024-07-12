package com.tujuhsembilan.app.model.position;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tujuhsembilan.app.model.talent.Talent;

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
public class TalentPositionId implements Serializable {
    private Talent talent;
    private Position position;
}