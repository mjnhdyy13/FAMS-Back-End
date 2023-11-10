package com.example.hcm23_java14_team2.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "output_standard")
public class OutputStandard {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "outputStandard", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Syllabus_Standard> syllabus_standardList = new ArrayList();

    @OneToMany(mappedBy = "outputStandard", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TrainingContent> trainingContents = new ArrayList();
}