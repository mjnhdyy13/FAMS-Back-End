package com.example.hcm23_java14_team2.service;
import com.example.hcm23_java14_team2.model.response.PageResponse;
import com.example.hcm23_java14_team2.model.response.Api.ApiResponse;
import com.example.hcm23_java14_team2.model.response.TrainingProgram.InsertTrainingProgramResponse;
import com.example.hcm23_java14_team2.model.response.TrainingProgram.UpdateTrainingProgramResponse;

import org.springframework.validation.BindingResult;

import com.example.hcm23_java14_team2.model.request.TrainingProgram.InsertTrainingProgramRequest;
import com.example.hcm23_java14_team2.model.request.TrainingProgram.UpdateTrainingProgramRequest;

import java.util.List;

public interface TrainingProgramService {
    PageResponse<List<UpdateTrainingProgramResponse>> getAllTrainingProgramsWithPage(String search, Integer page, Integer size);
    UpdateTrainingProgramResponse findById(Integer id);
    ApiResponse<List<InsertTrainingProgramResponse>> getAllTrainingPrograms(String search);
    ApiResponse<InsertTrainingProgramResponse> deleteTraining(Integer id);
    ApiResponse<UpdateTrainingProgramResponse> updateTrainingProgram(Integer id, UpdateTrainingProgramRequest trainingProgramRequest, BindingResult bindingResult);
    ApiResponse<Object> insertTrainingProgram(InsertTrainingProgramRequest trainingProgramRequest);
}

