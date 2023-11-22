package com.example.hcm23_java14_team2.model.mapper;


import com.example.hcm23_java14_team2.model.request.Class.ClassRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.hcm23_java14_team2.model.response.Class.ClassDetailResponse;
import com.example.hcm23_java14_team2.model.response.Class.ClassResponse;
import com.example.hcm23_java14_team2.model.response.Class.TrainingProgramViewClassResponse;
import com.example.hcm23_java14_team2.model.entities.Class;
import com.example.hcm23_java14_team2.model.entities.TrainingProgram;

import java.util.List;
@Mapper(componentModel = "spring")
public interface ClassMapper {
    ClassResponse toResponse(Class classDetail);
    List<ClassResponse> toResponselist(List<Class> classList);
    ClassDetailResponse toDetailResponse(Class classDetail);
    TrainingProgramViewClassResponse toTrainingProgramResponse(TrainingProgram trainingProgram);

    Class toEntity(ClassRequest classRequest);
}
