package com.example.hcm23_java14_team2.model.response;

import com.example.hcm23_java14_team2.model.entities.Enum.MethodTrainingContent;
import com.example.hcm23_java14_team2.model.entities.OutputStandard;
import com.example.hcm23_java14_team2.model.entities.TrainingUnit;
import com.example.hcm23_java14_team2.model.entities.Enum.DeleveryType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TrainingContentResponse {
    private Long id;
    private String nameContent;
    private DeleveryType deleveryType;
    private Integer duration;
    private MethodTrainingContent method;
    private Long trainingUnitId;
    private Long outputStandardId;

    // private TrainingUnit trainingUnit;
     private OutputStandard outputStandard;
}
