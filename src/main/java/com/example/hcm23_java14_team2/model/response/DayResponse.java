package com.example.hcm23_java14_team2.model.response;

import com.example.hcm23_java14_team2.model.entities.TrainingUnit;
import lombok.*;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DayResponse {
    private Integer day_number;
    private Long day_id;
    private List<TrainingUnit> trainingUnit;
}
