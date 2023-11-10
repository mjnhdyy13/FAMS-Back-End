package com.example.hcm23_java14_team2.model.entities.CompositeKey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class Training_SyllabusKey {
    @Column(name = "training_id")
    private Integer trainingProgramId;

    @Column(name = "syllabus_id")
    private Long syllabusId;
}
