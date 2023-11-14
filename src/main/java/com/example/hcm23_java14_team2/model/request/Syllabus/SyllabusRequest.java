package com.example.hcm23_java14_team2.model.request.Syllabus;

import com.example.hcm23_java14_team2.model.entities.Enum.Level;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusSyllabus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SyllabusRequest {
    private String codeName;
    private String topicName;
    private String technicalReq;
    private float version;
    //private Integer duration;
    private String courseObjective;
    private Level level;
    private String principle;
    private StatusSyllabus status;
}
