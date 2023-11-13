package com.example.hcm23_java14_team2.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrainingProgramViewClassResponse {
    private String name;
    private Integer duration;
    private String modifiedBy;
    private String modifiedDate;
    private List<SyllabusViewClassResponse> syllabuses;
}
