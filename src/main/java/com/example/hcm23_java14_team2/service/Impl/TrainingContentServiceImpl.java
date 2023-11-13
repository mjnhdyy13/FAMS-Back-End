package com.example.hcm23_java14_team2.service.Impl;

import java.util.List;

import com.example.hcm23_java14_team2.model.request.TrainingContent.TrainingContentUpdateRq;
import com.example.hcm23_java14_team2.model.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hcm23_java14_team2.model.entities.OutputStandard;
import com.example.hcm23_java14_team2.model.entities.TrainingContent;
import com.example.hcm23_java14_team2.model.entities.TrainingUnit;
import com.example.hcm23_java14_team2.model.mapper.TrainingContentMapper;
import com.example.hcm23_java14_team2.model.request.TrainingContent.TrainingContentRequest;
import com.example.hcm23_java14_team2.model.response.TrainingContentResponse;
import com.example.hcm23_java14_team2.repository.OutputStandardRepository;
import com.example.hcm23_java14_team2.repository.TrainingContentRepository;
import com.example.hcm23_java14_team2.repository.TrainingUnitRepository;
import com.example.hcm23_java14_team2.service.TrainingContentService;

@Service
public class TrainingContentServiceImpl implements TrainingContentService {
    @Autowired
    private TrainingContentRepository trainingContentRepository;

    @Autowired
    private TrainingContentMapper trainingContentMapper;

    @Autowired
    private TrainingUnitRepository trainingUnitRepository;

    @Autowired
    private OutputStandardRepository outputStandardRepository;

    @Autowired    
    @Override
    public List<TrainingContent> getAll() {
        List<TrainingContent> trainingContentList = trainingContentRepository.findAll();
//        List<TrainingContentResponse> trainingContentResponseList = trainingContentMapper.toResponseList(trainingContentList);
//        for(TrainingContent trainingContent:trainingContentList) {
//            var output_standard = outputStandardRepository.findById(trainingContent.getOutputStandard().getId());
//            trainingContent.setOutputStandard();
//        }
        return trainingContentList;
    }

    @Override
    public TrainingContentResponse getById(Long id) {
        TrainingContent trainingContent = trainingContentRepository.findById(id).orElse(null);
        if (trainingContent == null){
            return null;
        }
        TrainingContentResponse trainingContentResponse = trainingContentMapper.toResponse(trainingContent);
        return trainingContentResponse;
    }

    @Override
    public TrainingContentResponse insertTrainingContent(TrainingContentRequest request) {
        TrainingUnit trainingUnit = trainingUnitRepository.findById(request.getTrainingUnitId()).orElse(null);
        OutputStandard outputStandard = outputStandardRepository.findById(request.getOutputStandardId()).orElse(null);
        
        var trainingContent = TrainingContent.builder()
            .nameContent(request.getNameContent())
            .deleveryType(request.getDeleveryType())
            .duration(request.getDuration())
            .trainingUnit(trainingUnit)
            .outputStandard(outputStandard)
            .method(request.getMethod())
            .build();
        try {
            trainingContentRepository.save(trainingContent);
            TrainingContentResponse response = trainingContentMapper.toResponse(trainingContent);
            return response;
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public ApiResponse<Object> deleteTrainingContent(Long id) {
        try {
            var trainingContent = trainingContentRepository.findById(id);
            if(trainingContent.isPresent()){
                trainingContentRepository.deleteById(id);
                return ApiResponse.builder()
                        .statusCode("200")
                        .message("Delete successfully!")
                        .build();
            }
        } catch (Exception e) {
            return ApiResponse.builder()
                    .statusCode("401")
                    .message("Delete failed!")
                    .build();
        }
        return ApiResponse.builder()
                .statusCode("401")
                .message("Delete failed!")
                .build();
    }

    @Override
    public ApiResponse<Object> updateTrainingContent(Long id,TrainingContentUpdateRq request) {
        try {
            TrainingContent trainingContent = trainingContentRepository.findById(id).get();
            if(trainingContent.getId()!=null){
                trainingContent.setNameContent(request.getNameContent());
                trainingContent.setOutputStandard(outputStandardRepository.findById(request.getOutputStandardId()).get());
                trainingContent.setDuration(request.getDuration());
                trainingContent.setMethod(request.getMethod());
                trainingContent.setDeleveryType(request.getDeleveryType());
                trainingContentRepository.save(trainingContent);
                return ApiResponse.builder()
                        .statusCode("200")
                        .data(trainingContent)
                        .message("Successfully!")
                        .build();
            }
        } catch (Exception e){
            return ApiResponse.builder()
                    .statusCode("401")
                    .message("Update failed!")
                    .build();
        }
        return ApiResponse.builder()
                .statusCode("401")
                .message("Content is not existed!")
                .build();
    }

}
