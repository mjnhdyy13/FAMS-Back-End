package com.example.hcm23_java14_team2.service.Impl;

import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.model.entities.Syllabus;
import com.example.hcm23_java14_team2.model.entities.TrainingProgram;
import com.example.hcm23_java14_team2.model.entities.Training_Syllabus;
import com.example.hcm23_java14_team2.model.response.ApiResponse;
import com.example.hcm23_java14_team2.repository.SyllabusRepository;
import com.example.hcm23_java14_team2.repository.TrainingProgramRepository;
import com.example.hcm23_java14_team2.repository.Training_SyllabusRepository;
import com.example.hcm23_java14_team2.service.TrainingSyllabusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class TrainingSyllabusServiceImpl implements TrainingSyllabusService {
    @Autowired
    Training_SyllabusRepository trainingSyllabusRepository;
    @Autowired
    TrainingProgramRepository trainingProgramRepository;
    @Autowired
    SyllabusRepository syllabusRepository;
    @Override
    public ApiResponse<Object> deleteTrainingSyllabus(Integer idTrainingSyllabus, Long idSyllabus, BindingResult bindingResult) {
        try {
            Syllabus syllabus = syllabusRepository.findById(idSyllabus)
                    .orElseThrow(() -> new NotFoundException("Syllabus Not Found"));
            TrainingProgram trainingProgram = trainingProgramRepository.findById(idTrainingSyllabus)
                    .orElseThrow(() -> new NotFoundException("TrainingProgram Not Found"));

            Optional<Training_Syllabus> training_syllabus = trainingSyllabusRepository.findByTrainingIdAndSyllabusId(idTrainingSyllabus,idSyllabus);

            if(training_syllabus.isEmpty()){
                return ApiResponse.builder()
                        .statusCode("400")
                        .message("Remove Syllabus of TrainingProgram fails")
                        .build();
            }
            else {
                trainingSyllabusRepository.deleteById(training_syllabus.get().getId());
                return ApiResponse.builder()
                        .statusCode("200")
                        .message("Delete complete")
                        .build();
            }
        }
        catch (Exception e){
            return ApiResponse.builder()
                    .statusCode("400")
                    .message("Remove Syllabus of TrainingProgram fails")
                    .build();
        }
    }

    @Override
    public ApiResponse<Object> addTrainingSyllabus(Integer idTrainingSyllabus, Long idSyllabus, BindingResult bindingResult) {
        try {
            Syllabus syllabus = syllabusRepository.findById(idSyllabus)
                    .orElseThrow(() -> new NotFoundException("Syllabus Not Found"));
            TrainingProgram trainingProgram = trainingProgramRepository.findById(idTrainingSyllabus)
                    .orElseThrow(() -> new NotFoundException("TrainingProgram Not Found"));

            Optional<Training_Syllabus> training_syllabus = trainingSyllabusRepository.findByTrainingIdAndSyllabusId(idTrainingSyllabus,idSyllabus);
            Training_Syllabus test = Training_Syllabus.builder().syllabus(syllabus).trainingProgram(trainingProgram).build();

            if(training_syllabus.isEmpty()){
                trainingSyllabusRepository.save(test);
                return ApiResponse.builder()
                        .statusCode("200")
                        .message("Add Syllabus to TrainingProgram successfully!!")
                        .build();
            }
            return ApiResponse.builder()
                    .statusCode("400")
                    .message("Add Syllabus to TrainingProgram fails")
                    .build();
        }
        catch (Exception e){
            return ApiResponse.builder()
                    .statusCode("400")
                    .message("Add Syllabus to TrainingProgram fails")
                    .build();
        }
    }
}
