package com.example.hcm23_java14_team2.model.response.Class;

import com.example.hcm23_java14_team2.model.entities.TrainingProgram;
import com.example.hcm23_java14_team2.model.entities.Enum.AttendeeClass;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusClass;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClassResponse {
    private Long id;
    private String className;
    private String classCode;
    private Integer duration;
    private String location;
    private String FSU;
    private String startTime;
    private String endTime;
    private StatusClass status;
    private String startDate;
    private String endDate;
    private AttendeeClass attendee;
    private String createBy;
    private String createDate;
    private String modifiedBy;
    private String modifiedDate;

    private TrainingProgramViewClassResponse trainingProgram;
}
