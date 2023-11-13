package com.example.hcm23_java14_team2.model.mapper;

import com.example.hcm23_java14_team2.model.entities.Class;
import com.example.hcm23_java14_team2.model.response.ClassResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassMapper {
    ClassResponse toResponse(Class classes);


    List<ClassResponse> toResponselist(List<Class> classList);
}
