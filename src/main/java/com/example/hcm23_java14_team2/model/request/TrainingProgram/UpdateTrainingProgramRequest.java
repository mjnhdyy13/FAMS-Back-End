package com.example.hcm23_java14_team2.model.request.TrainingProgram;

import com.example.hcm23_java14_team2.model.entities.Enum.StatusTrainingProgram;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTrainingProgramRequest {
    private String code;
    private String name;
    private String startTime;
    private Integer duration;
    private StatusTrainingProgram status;
    private Long userId;
}
