package com.example.hcm23_java14_team2.model.response;

import java.util.Date;
import java.util.List;

import com.example.hcm23_java14_team2.model.entities.User;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusTrainingProgram;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import com.example.hcm23_java14_team2.model.entities.Class;
import com.example.hcm23_java14_team2.model.entities.Syllabus;
import com.example.hcm23_java14_team2.model.entities.Training_Syllabus;

@Getter
@Setter
@Builder
public class TrainingProgramResponse {
    private Integer id;
    private String name;
    private String code;
    private Integer duration;
    private String startTime;
    private StatusTrainingProgram status;
    private String CreateBy;
    private String CreateOn;

    private List<Syllabus> syllabusList;
}
