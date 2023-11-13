package com.example.hcm23_java14_team2.model.response;

import com.example.hcm23_java14_team2.model.entities.Enum.AttendeeClass;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

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
    private AttendeeClass attendee;
    private String trainerName;
    private String adminName;
    private TrainingProgramViewClassResponse trainingProgram;
}
