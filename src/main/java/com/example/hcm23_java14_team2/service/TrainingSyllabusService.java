package com.example.hcm23_java14_team2.service;

import com.example.hcm23_java14_team2.model.response.ApiResponse;
import org.springframework.validation.BindingResult;

public interface TrainingSyllabusService {
    ApiResponse<Object> deleteTrainingSyllabus(Integer idTrainingSyllabus, Long idSyllabus, BindingResult bindingResult);
    ApiResponse<Object> addTrainingSyllabus(Integer idTrainingSyllabus, Long idSyllabus, BindingResult bindingResult);
}
