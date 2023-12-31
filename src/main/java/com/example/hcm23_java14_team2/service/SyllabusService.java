package com.example.hcm23_java14_team2.service;

import com.example.hcm23_java14_team2.model.entities.Syllabus;
import com.example.hcm23_java14_team2.model.request.Syllabus.SyllabusRequest;
import com.example.hcm23_java14_team2.model.response.PageResponse;
import com.example.hcm23_java14_team2.model.response.Api.ApiResponse;
import com.example.hcm23_java14_team2.model.response.Syllabus.SyllabusResponse;
import com.example.hcm23_java14_team2.model.response.Syllabus.UpdateSyllabusResponse;
import com.example.hcm23_java14_team2.model.response.TrainingProgram.InsertTrainingProgramResponse;

import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface SyllabusService {
    PageResponse<List<SyllabusResponse>> getAllSyllabusWithPage(String search, Integer page, Integer size);
    ApiResponse<List<SyllabusResponse>> getAllSyllabus(String search);
    SyllabusResponse findById(Long id);
    Syllabus findByID(Long id);
    UpdateSyllabusResponse updateSyllabus(Long id, SyllabusRequest syllabusRequest, BindingResult bindingResult);
    ApiResponse<Object> insertSyllabus(SyllabusRequest request);
    String deleteSyllabus(Long id);
    void importFile(MultipartFile file);
    ByteArrayInputStream load(Long id);
}

