package com.example.hcm23_java14_team2.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.hcm23_java14_team2.exception.ValidationException;
import com.example.hcm23_java14_team2.model.entities.User;
import com.example.hcm23_java14_team2.model.request.TrainingProgramRequest;
import com.example.hcm23_java14_team2.model.response.PageResponse;
import com.example.hcm23_java14_team2.repository.UserRepository;
import com.example.hcm23_java14_team2.util.ValidatorUtil;
import com.example.hcm23_java14_team2.validator.TrainingProgramValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.example.hcm23_java14_team2.model.response.ApiResponse;
import com.example.hcm23_java14_team2.exception.ApplicationException;
import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.model.entities.TrainingProgram;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusTrainingProgram;
import com.example.hcm23_java14_team2.model.mapper.TrainingProgramMapper;
import com.example.hcm23_java14_team2.model.response.TrainingProgramResponse;
import com.example.hcm23_java14_team2.repository.TrainingProgramRepository;
import com.example.hcm23_java14_team2.service.TrainingProgramService;
import org.springframework.validation.BindingResult;

@Service
public class TrainingProgramServiceImpl implements TrainingProgramService {

    @Autowired
    private TrainingProgramRepository trainingProgramRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidatorUtil validatorUtil;
    @Autowired
    private TrainingProgramMapper trainingProgramMapper;
    @Autowired
    private TrainingProgramValidator trainingProgramValidator;


    @Override
    public PageResponse<List<TrainingProgramResponse>> getAllTrainingProgramsWithPage(String search, Integer page, Integer size){
        Page<TrainingProgram> t = trainingProgramRepository.searchByNameWithPage(search,PageRequest.of(page-1,size));
        List<TrainingProgram>  PageTraining = t.getContent();
        List<TrainingProgramResponse> trainingProgramResponses = new ArrayList<>();

        for(TrainingProgram item : PageTraining){
            var trainingProgramResponse = trainingProgramMapper.toResponse(item);
            trainingProgramResponse.setUserNameCreate(item.getUser().getName());
            trainingProgramResponses.add(trainingProgramResponse);
        }

        PageResponse<List<TrainingProgramResponse>> listPageResponse = new PageResponse<>();
        listPageResponse.ok(trainingProgramResponses);
        double total = Math.ceil((double)t.getTotalElements() / size);
        listPageResponse.setTotalPage(total);
        return  listPageResponse;

    }


    @Override
    public TrainingProgramResponse findById(Integer id) {
        TrainingProgram trainingProgram = trainingProgramRepository.findById(id).orElse(null);
        if (trainingProgram == null){
            return null;
        }
        TrainingProgramResponse trainingProgramResponse = trainingProgramMapper.toResponse(trainingProgram);
        trainingProgramResponse.setUserNameCreate(trainingProgram.getUser().getName());
        return trainingProgramResponse;
    }

    @Override
    public List<TrainingProgramResponse> getAllTrainingPrograms(String search) {
        List<TrainingProgram> trainingPrograms = trainingProgramRepository.searchByName(search);
        List<TrainingProgramResponse> trainingProgramResponses = new ArrayList<>();

        for (TrainingProgram item : trainingPrograms){
            var trainingProgramResponse = trainingProgramMapper.toResponse(item);
            trainingProgramResponse.setUserNameCreate(item.getUser().getName());
            trainingProgramResponses.add(trainingProgramResponse);
        }
        return trainingProgramResponses;
    }
    
    @Override
    public TrainingProgramResponse deleteTraining(Integer id){
        try {
            TrainingProgram trainingProgram = trainingProgramRepository.findById(id).orElse(null);
            if (trainingProgram == null) {
                throw new NotFoundException("TrainingProgram Not Found");
            }
            // Update
            trainingProgram.setStatus(StatusTrainingProgram.valueOf("INACTIVE"));
            trainingProgramRepository.saveAndFlush(trainingProgram);

            return trainingProgramMapper.toResponse(trainingProgram);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }
    public TrainingProgramResponse updateTrainingProgram(Integer id, TrainingProgramRequest trainingProgramRequest, BindingResult bindingResult) {
        try {
            //checking trainingprogram id
            TrainingProgram existingTrainingProgram = trainingProgramRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Syllabus Not Found"));

            trainingProgramValidator.validate(trainingProgramRequest, bindingResult);
            if (bindingResult.hasErrors()) {
                Map<String, String> validationErrors = validatorUtil.toErrors(bindingResult.getFieldErrors());
                throw new ValidationException(validationErrors);
            }
            //check value
            if (bindingResult.hasErrors()) {
                Map<String, String> validationErrors = validatorUtil.toErrors(bindingResult.getFieldErrors());
                throw new ValidationException(validationErrors);
            }
            if (trainingProgramRequest.getCode() != null) {
                existingTrainingProgram.setCode(trainingProgramRequest.getCode());
            }
            if (trainingProgramRequest.getName() != null) {
                existingTrainingProgram.setName(trainingProgramRequest.getName());
            }
            if (trainingProgramRequest.getStartTime() != null) {
                existingTrainingProgram.setStartTime(trainingProgramRequest.getStartTime());
            }
            if (trainingProgramRequest.getDuration() != null) {
                existingTrainingProgram.setDuration(trainingProgramRequest.getDuration());
            }
            if (trainingProgramRequest.getStatus() != null) {
                existingTrainingProgram.setStatus(trainingProgramRequest.getStatus());
            }
            if (trainingProgramRequest.getUserId() != null) {
                User user = userRepository.findById(trainingProgramRequest.getUserId()).orElseThrow(() -> new NotFoundException(""));
                existingTrainingProgram.setUser(user);
            }

            trainingProgramRepository.saveAndFlush(existingTrainingProgram);
            return trainingProgramMapper.toResponse(existingTrainingProgram);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public ApiResponse<Object> insertTrainingProgram(TrainingProgramRequest trainingProgramRequest) {
        User userRequest = new User();
        if (trainingProgramRequest.getUserId() != null) {
            userRequest = userRepository.findById(trainingProgramRequest.getUserId()).orElseThrow(() -> new NotFoundException("Not Found User"));
        }

        var trainingProgram = TrainingProgram.builder()
                .code(trainingProgramRequest.getCode())
                .name(trainingProgramRequest.getName())
                .startTime(trainingProgramRequest.getStartTime())
                .duration(trainingProgramRequest.getDuration())
                .status(trainingProgramRequest.getStatus())
                .user(userRequest)
                .build();
        try {
            trainingProgramRepository.save(trainingProgram);
            var response = ApiResponse
                    .builder()
                    .statusCode("200")
                    .message("Insert successes")
                    .data(trainingProgramMapper.toResponse(trainingProgram))
                    .build();
            return response;
        }
        catch (Exception e) {
            return ApiResponse
                    .builder()
                    .statusCode("401")
                    .message("Insert failed")
                    .build();
        }
    }
}
