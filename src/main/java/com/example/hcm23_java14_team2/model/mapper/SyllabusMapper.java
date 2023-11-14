package com.example.hcm23_java14_team2.model.mapper;


import org.mapstruct.Mapper;

import com.example.hcm23_java14_team2.model.entities.Syllabus;
import com.example.hcm23_java14_team2.model.response.Syllabus.SyllabusResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SyllabusMapper {

    SyllabusResponse toResponse(Syllabus syllabus);


    List<SyllabusResponse> toResponselist(List<Syllabus> syllabusList);
}

