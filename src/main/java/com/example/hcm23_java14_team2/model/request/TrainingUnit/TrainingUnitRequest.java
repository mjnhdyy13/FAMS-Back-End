package com.example.hcm23_java14_team2.model.request.TrainingUnit;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TrainingUnitRequest {
    private Long syllabus_id;
    private String unitName;
    private Integer unitNum;
    private Long day_id;
}
