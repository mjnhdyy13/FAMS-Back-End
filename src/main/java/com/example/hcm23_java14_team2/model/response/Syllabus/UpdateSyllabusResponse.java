package com.example.hcm23_java14_team2.model.response.Syllabus;

import com.example.hcm23_java14_team2.model.entities.Enum.Level;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusSyllabus;
import com.example.hcm23_java14_team2.model.response.OutputStandard.OutputStandardResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UpdateSyllabusResponse {
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
    private String modifiedBy;
    private String modifiedDate;

    private List<OutputStandardResponse> outputStandardList;
}
