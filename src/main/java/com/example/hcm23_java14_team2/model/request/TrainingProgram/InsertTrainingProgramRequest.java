package com.example.hcm23_java14_team2.model.request.TrainingProgram;

// import com.example.hcm23_java14_team2.model.entities.Enum.StatusTrainingProgram;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InsertTrainingProgramRequest {
    private String name;
    private List<Long> syllabusListId;
}