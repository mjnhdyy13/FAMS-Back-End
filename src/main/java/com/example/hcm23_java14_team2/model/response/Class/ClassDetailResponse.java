package com.example.hcm23_java14_team2.model.response.Class;

import com.example.hcm23_java14_team2.model.entities.Enum.AttendeeClass;

import com.example.hcm23_java14_team2.model.entities.Enum.StatusClass;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClassDetailResponse {
    private String createBy;
    private String createDate;
    private String modifiedBy;
    private String modifiedDate;
    private String className;
    private String classCode;
    private String startTime;
    private String endTime;
    private String location;
    private String fsu;
    private StatusClass status;
    private AttendeeClass attendee;
    private List<String> trainerName;
    private List<String> adminName;
    private TrainingProgramViewClassResponse trainingProgram;
}
