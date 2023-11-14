package com.example.hcm23_java14_team2.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.hcm23_java14_team2.model.entities.TrainingProgram;
import com.example.hcm23_java14_team2.model.request.TrainingProgram.UpdateTrainingProgramRequest;
import com.example.hcm23_java14_team2.model.response.TrainingProgram.InsertTrainingProgramResponse;
import com.example.hcm23_java14_team2.model.response.TrainingProgram.UpdateTrainingProgramResponse;


@Mapper(componentModel = "spring")
public interface TrainingProgramMapper {
//    @Mapping(source = "user.id", target = "userId")
    InsertTrainingProgramResponse toResponse(TrainingProgram trainingProgram);
    UpdateTrainingProgramResponse toUpdateResponse(TrainingProgram trainingProgram);
    List<InsertTrainingProgramResponse> toListResponses(List<TrainingProgram> trainingPrograms);
}
