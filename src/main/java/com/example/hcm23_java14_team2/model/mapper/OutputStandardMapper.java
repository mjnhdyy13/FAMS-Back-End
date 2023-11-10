package com.example.hcm23_java14_team2.model.mapper;

import com.example.hcm23_java14_team2.model.entities.OutputStandard;
import com.example.hcm23_java14_team2.model.response.OutputStandardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OutputStandardMapper {
    @Mapping(source = "outputStandard.id",target = "id")
    @Mapping(source = "outputStandard.name",target = "name")
    OutputStandardResponse toResponse(OutputStandard outputStandard);

    List<OutputStandardResponse> toResponseList(List<OutputStandard> outputStandardList);
}

