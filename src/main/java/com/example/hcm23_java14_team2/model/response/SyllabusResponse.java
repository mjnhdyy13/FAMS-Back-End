package com.example.hcm23_java14_team2.model.response;

import com.example.hcm23_java14_team2.model.entities.Enum.Level;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusSyllabus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SyllabusResponse {
    private Long id;
    private String codeName;
    private String topicName;
    private String technicalReq;
    private float version;
    private Integer duration;
    private String courseObjective;
    private Level level;
    private String principle;
    private StatusSyllabus status;

    private List<OutputStandardResponse> outputStandardList;

}

