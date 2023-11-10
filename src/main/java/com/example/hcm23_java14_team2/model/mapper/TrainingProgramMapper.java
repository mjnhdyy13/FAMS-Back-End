package com.example.hcm23_java14_team2.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.hcm23_java14_team2.model.entities.TrainingProgram;
import com.example.hcm23_java14_team2.model.response.TrainingProgramResponse;

@Mapper(componentModel = "spring")
public interface TrainingProgramMapper {
//    @Mapping(source = "user.id", target = "userId")
    TrainingProgramResponse toResponse(TrainingProgram trainingProgram);
    List<TrainingProgramResponse> toListResponses(List<TrainingProgram> trainingPrograms);
}
