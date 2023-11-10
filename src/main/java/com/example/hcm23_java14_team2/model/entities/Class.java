package com.example.hcm23_java14_team2.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "class")
public class Class extends BaseEntity{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_name")
    private String className;
    @Column(name = "class_code")
    private String classCode;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "status")
    private String status;
    @Column(name = "location")
    private String location;
    @Column(name = "fsu")
    private String FSU;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "attendee")
    private String attendee;

    @OneToMany(mappedBy = "classRoom", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Class_User> classUserList = new ArrayList();

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "training_program_id")
    private TrainingProgram trainingProgram;
}
