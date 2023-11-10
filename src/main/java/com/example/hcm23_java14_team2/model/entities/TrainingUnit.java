package com.example.hcm23_java14_team2.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Table(name = "training_unit")
public class TrainingUnit {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unitName")
    private String unitName;

    @Column(name="unitNum")
    private Integer unitNum;

//    @ManyToOne()
//    @JsonIgnore
//    @JoinColumn(name = "syllabus_id")
//    private Syllabus syllabus;

    @OneToMany(mappedBy = "trainingUnit", cascade = CascadeType.ALL)
    private List<TrainingContent> trainingContentList = new ArrayList();

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "day_id")
    private DaySyllabus daySyllabus;

}
