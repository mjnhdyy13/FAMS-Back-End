package com.example.hcm23_java14_team2.model.request;

import com.example.hcm23_java14_team2.model.entities.Enum.StatusTrainingProgram;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TrainingProgramRequest {
    private String code;
    private String name;
    private String startTime;
    private Integer duration;
    private StatusTrainingProgram status;
    private Long userId;
}
