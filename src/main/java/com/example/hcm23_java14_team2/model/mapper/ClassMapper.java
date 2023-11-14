package com.example.hcm23_java14_team2.model.mapper;


import org.mapstruct.Mapper;

import com.example.hcm23_java14_team2.model.response.Class.ClassResponse;
import com.example.hcm23_java14_team2.model.entities.Class;

import java.util.List;
@Mapper(componentModel = "spring")
public interface ClassMapper {
    @Mapping(source = "trainingProgram.id",target = "TrainingProgramId")
    ClassResponse toResponse(Class aclass);
    List<ClassResponse> toResponselist(List<Class> classList);
}
