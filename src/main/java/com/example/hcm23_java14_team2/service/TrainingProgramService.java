package com.example.hcm23_java14_team2.service;
import com.example.hcm23_java14_team2.model.request.TrainingProgramRequest;
import com.example.hcm23_java14_team2.model.response.PageResponse;
import com.example.hcm23_java14_team2.model.response.TrainingProgramResponse;
import org.springframework.validation.BindingResult;
import com.example.hcm23_java14_team2.model.response.ApiResponse;

import java.util.List;

public interface TrainingProgramService {
    PageResponse<List<TrainingProgramResponse>> getAllTrainingProgramsWithPage(String search, Integer page, Integer size);
    TrainingProgramResponse findById(Integer id);
    ApiResponse<List<TrainingProgramResponse>> getAllTrainingPrograms(String search);
    TrainingProgramResponse deleteTraining(Integer id);
    TrainingProgramResponse updateTrainingProgram(Integer id, TrainingProgramRequest trainingProgramRequest, BindingResult bindingResult);
    ApiResponse<Object> insertTrainingProgram(TrainingProgramRequest trainingProgramRequest);
}

