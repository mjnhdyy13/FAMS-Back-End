package com.example.hcm23_java14_team2.model.request.TrainingUnit;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteDayInUnitDayRequest {
    private Long id;
    private Integer day;
}
