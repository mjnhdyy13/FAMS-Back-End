package com.example.hcm23_java14_team2.model.entities;

import com.example.hcm23_java14_team2.model.entities.Enum.DeleveryType;
import com.example.hcm23_java14_team2.model.entities.Enum.MethodTrainingContent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "training_content")
public class TrainingContent {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_content")
    private String nameContent;

    @Column(name = "delivery_type")
    @Enumerated(EnumType.STRING)
    private DeleveryType deleveryType;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "method")
    @Enumerated(EnumType.STRING)
    private MethodTrainingContent method;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "unit_id")
    private TrainingUnit trainingUnit;

    @ManyToOne()
    //@JsonIgnore
    @JoinColumn(name = "output_standard_id")
    private OutputStandard outputStandard;
}