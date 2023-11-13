package com.example.hcm23_java14_team2.model.response;

import com.example.hcm23_java14_team2.model.entities.Enum.StatusSyllabus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SyllabusViewClassResponse {
    private String createBy;
    private String createDate;
    private String topicName;
    private String codeName;
    private float version;
    private StatusSyllabus status;
}
