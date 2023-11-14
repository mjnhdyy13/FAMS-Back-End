package com.example.hcm23_java14_team2.model.response.TrainingProgram;

import java.util.List;

import com.example.hcm23_java14_team2.model.entities.Syllabus;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusTrainingProgram;

import lombok.*;

@Getter @Setter
public class UpdateTrainingProgramResponse {
    private Integer id;
    private String name;
    private String code;
    private Integer duration;
    private String startTime;
    private StatusTrainingProgram status;
    private String CreateBy;
    private String CreateDate;
    private String ModifiedBy;
    private String ModifiedDate;

    private List<Syllabus> syllabusList;
}
