package com.example.hcm23_java14_team2.service;

import org.springframework.validation.BindingResult;

import com.example.hcm23_java14_team2.model.response.Api.ApiResponse;

public interface TrainingSyllabusService {
    ApiResponse<Object> deleteTrainingSyllabus(Integer idTrainingSyllabus, Long idSyllabus, BindingResult bindingResult);
    ApiResponse<Object> addTrainingSyllabus(Integer idTrainingSyllabus, Long idSyllabus, BindingResult bindingResult);
}
