package com.example.hcm23_java14_team2.service.Impl;

import java.util.List;

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
    public TrainingContentResponse updateTrainingContent(Long id, TrainingContentRequest trainingContentRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTrainingContent'");
    }

    @Override
    public TrainingContentResponse deleteTrainingContent(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteTrainingContent'");
    }
    
}
