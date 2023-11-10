package com.example.hcm23_java14_team2.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "syllabus_standard")
public class Syllabus_Standard {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "syllabus_id")
    private Syllabus syllabus;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "standard_id")
    private OutputStandard outputStandard;
}

