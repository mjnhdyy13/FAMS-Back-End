package com.example.hcm23_java14_team2.service;

import java.util.List;

import com.example.hcm23_java14_team2.model.entities.TrainingContent;
import com.example.hcm23_java14_team2.model.request.TrainingContentRequest;
import com.example.hcm23_java14_team2.model.response.TrainingContentResponse;

public interface TrainingContentService {
    List<TrainingContent> getAll();
    TrainingContentResponse getById(Long id);
    TrainingContentResponse insertTrainingContent(TrainingContentRequest trainingContentRequest);
    TrainingContentResponse updateTrainingContent(Long id, TrainingContentRequest trainingContentRequest);
    TrainingContentResponse deleteTrainingContent(Long id);

}