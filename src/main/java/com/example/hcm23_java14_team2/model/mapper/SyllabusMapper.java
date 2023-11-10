package com.example.hcm23_java14_team2.model.mapper;

import com.example.hcm23_java14_team2.model.entities.Syllabus;
import org.mapstruct.Mapper;
import com.example.hcm23_java14_team2.model.response.SyllabusResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SyllabusMapper {

    SyllabusResponse toResponse(Syllabus syllabus);


    List<SyllabusResponse> toResponselist(List<Syllabus> syllabusList);
}

