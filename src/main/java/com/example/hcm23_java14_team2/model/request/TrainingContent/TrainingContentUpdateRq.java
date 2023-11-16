package com.example.hcm23_java14_team2.model.request.TrainingContent;

import com.example.hcm23_java14_team2.model.entities.Enum.DeleveryType;
import com.example.hcm23_java14_team2.model.entities.Enum.MethodTrainingContent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainingContentUpdateRq {
    private String nameContent;
    private DeleveryType deleveryType;
    private Integer duration;
    private MethodTrainingContent method;
    private Long outputStandardId;
}
