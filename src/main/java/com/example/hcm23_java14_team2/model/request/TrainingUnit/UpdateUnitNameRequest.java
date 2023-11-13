package com.example.hcm23_java14_team2.model.request.TrainingUnit;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUnitNameRequest {
    private Long unit_id;
    private String name;
}
