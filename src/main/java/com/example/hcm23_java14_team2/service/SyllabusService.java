package com.example.hcm23_java14_team2.service;

import com.example.hcm23_java14_team2.model.entities.Syllabus;
import com.example.hcm23_java14_team2.model.request.Syllabus.SyllabusRequest;
import com.example.hcm23_java14_team2.model.response.ApiResponse;
import com.example.hcm23_java14_team2.model.response.SyllabusResponse;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface SyllabusService {
    List<SyllabusResponse> getAllSyllabus();
    SyllabusResponse findById(Long id);
    Syllabus findByID(Long id);
    SyllabusResponse updateSyllabus(Long id,SyllabusRequest syllabusRequest, BindingResult bindingResult);
    ApiResponse<Object> insertSyllabus(SyllabusRequest request);
    String deleteSyllabus(Long id);
}

