package com.example.hcm23_java14_team2.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "training_syllabus")
public class Training_Syllabus {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "training_id")
    private TrainingProgram trainingProgram;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "syllabus_id")
    private Syllabus syllabus;
}
