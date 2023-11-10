package com.example.hcm23_java14_team2.model.entities;

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
@Table(name = "day_in_syllabus")
public class DaySyllabus {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_number")
    private Integer day;

    @OneToMany(mappedBy = "daySyllabus", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TrainingUnit> trainingUnitList = new ArrayList();

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "syllabus_id")
    private Syllabus syllabus;
}
