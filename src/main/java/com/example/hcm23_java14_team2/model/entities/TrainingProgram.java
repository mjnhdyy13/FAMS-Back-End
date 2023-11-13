package com.example.hcm23_java14_team2.model.entities;

import com.example.hcm23_java14_team2.model.entities.Enum.StatusTrainingProgram;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "training_program")
public class TrainingProgram extends BaseEntity{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "status")
    private StatusTrainingProgram status;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "trainingProgram", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Class> ClassList = new ArrayList();

    @OneToMany(mappedBy = "trainingProgram", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Training_Syllabus> training_syllabusList = new ArrayList();
}
