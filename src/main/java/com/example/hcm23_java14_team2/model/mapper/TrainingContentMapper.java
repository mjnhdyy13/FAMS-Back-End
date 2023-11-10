package com.example.hcm23_java14_team2.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.hcm23_java14_team2.model.entities.TrainingContent;
import com.example.hcm23_java14_team2.model.response.TrainingContentResponse;

@Mapper(componentModel = "spring")
public interface TrainingContentMapper {
    @Mapping(source = "trainingUnit.id",target = "trainingUnitId")
    @Mapping(source = "outputStandard.id",target = "outputStandardId")
    TrainingContentResponse toResponse(TrainingContent trainingContent);

    List<TrainingContentResponse> toResponseList(List<TrainingContent> trainingContent);
}
