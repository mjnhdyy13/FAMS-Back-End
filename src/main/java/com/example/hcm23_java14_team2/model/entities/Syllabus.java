package com.example.hcm23_java14_team2.model.entities;

import com.example.hcm23_java14_team2.model.entities.Enum.Level;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusSyllabus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "syllabus")
public class Syllabus extends BaseEntity{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String codeName;

    @Column(name = "name")
    private String topicName;

    @Column(name = "technical_req",columnDefinition = "LONGTEXT")
    private String technicalReq; //technical requirement

    @Column(name = "version")
    private float version;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "course_objective",columnDefinition = "LONGTEXT")
    private String courseObjective; //CourseObjective

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    private Level level;  //training audience

    @Column(name = "principle",columnDefinition = "LONGTEXT")
    private String principle;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusSyllabus status;

    @OneToMany(mappedBy = "syllabus", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Training_Syllabus> training_syllabusList = new ArrayList();

    @OneToMany(mappedBy = "syllabus", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Syllabus_Standard> syllabus_standardList = new ArrayList();

//    @OneToMany(mappedBy = "syllabus", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<TrainingUnit> trainingUnitList = new ArrayList();

    @OneToMany(mappedBy = "syllabus", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DaySyllabus> daySyllabusList = new ArrayList();
}
