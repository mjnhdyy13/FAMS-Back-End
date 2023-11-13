package com.example.hcm23_java14_team2.model.request.Class;

import com.example.hcm23_java14_team2.model.entities.Enum.AttendeeClass;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusClass;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ClassSearchRequest {
    private String classNameOrCode;
    private List<String> locations;
    private String fromDate;
    private String toDate;
    private List<StatusClass> statuses;
    private List<AttendeeClass> attendee;
    private String fsu;
    private String trainerName;
}
