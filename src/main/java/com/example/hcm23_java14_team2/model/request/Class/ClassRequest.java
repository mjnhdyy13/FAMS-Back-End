package com.example.hcm23_java14_team2.model.request.Class;

import com.example.hcm23_java14_team2.model.entities.Enum.AttendeeClass;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusClass;
import lombok.Getter;
import lombok.Setter;
import java.util.List;


@Getter
@Setter
public class ClassRequest {
    private Long id;
    private String className;
    private String classCode;
    private Integer duration;
    private StatusClass status;
    private String location;
    private String FSU;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private AttendeeClass attendee;
    private String modifiedBy;
    private Integer TrainingProgramId;


}
