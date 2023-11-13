package com.example.hcm23_java14_team2.service.Impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.hcm23_java14_team2.exception.ValidationException;
import com.example.hcm23_java14_team2.model.entities.User;
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
import com.example.hcm23_java14_team2.model.entities.Syllabus;
import com.example.hcm23_java14_team2.model.entities.TrainingProgram;
import com.example.hcm23_java14_team2.model.entities.Training_Syllabus;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusTrainingProgram;
import com.example.hcm23_java14_team2.model.mapper.TrainingProgramMapper;
import com.example.hcm23_java14_team2.model.request.TrainingProgram.InsertTrainingProgramRequest;
import com.example.hcm23_java14_team2.model.request.TrainingProgram.UpdateTrainingProgramRequest;
import com.example.hcm23_java14_team2.model.response.TrainingProgramResponse;
import com.example.hcm23_java14_team2.repository.SyllabusRepository;
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
    @Autowired
    private SyllabusRepository syllabusRepository;


    @Override
    public PageResponse<List<TrainingProgramResponse>> getAllTrainingProgramsWithPage(String search, Integer page, Integer size){
        Page<TrainingProgram> t = trainingProgramRepository.searchByNameWithPage(search,PageRequest.of(page-1,size));
        List<TrainingProgram>  PageTraining = t.getContent();
        List<TrainingProgramResponse> trainingProgramResponses = new ArrayList<>();

        for(TrainingProgram item : PageTraining){
            var trainingProgramResponse = trainingProgramMapper.toResponse(item);
            if (item.getUser() != null){
                trainingProgramResponse.setUserNameCreate(item.getUser().getName());
            }
            // get syllabus list for each training program
            List<Syllabus> syllabusList = new ArrayList<>();
            for (Training_Syllabus ts : item.getTraining_syllabusList()){
                syllabusList.add(ts.getSyllabus());
            }
            trainingProgramResponse.setSyllabusList(syllabusList);
            // trainingProgramResponse.setUserNameCreate(item.getUser().getName());
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
    public ApiResponse<List<TrainingProgramResponse>> getAllTrainingPrograms(String search) {
        List<TrainingProgram> trainingPrograms = trainingProgramRepository.searchByName(search);
        List<TrainingProgramResponse> trainingProgramResponses = new ArrayList<>();

        for (TrainingProgram item : trainingPrograms){
            var trainingProgramResponse = trainingProgramMapper.toResponse(item);
            trainingProgramResponse.setUserNameCreate(item.getUser().getName());
            trainingProgramResponses.add(trainingProgramResponse);
        }
        ApiResponse<List<TrainingProgramResponse>> apiResponse = new ApiResponse<>();
        apiResponse.ok(trainingProgramResponses);
        return apiResponse;
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
    public TrainingProgramResponse updateTrainingProgram(Integer id, UpdateTrainingProgramRequest trainingProgramRequest, BindingResult bindingResult) {
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
    public ApiResponse<Object> insertTrainingProgram(InsertTrainingProgramRequest trainingProgramRequest) {
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        try{
            List<Training_Syllabus> listTraining_Syllabus = new ArrayList<>();

            TrainingProgram trainingProgram = TrainingProgram.builder()
                .name(trainingProgramRequest.getName())
                .status(StatusTrainingProgram.DRAFT)
                .build();

            //set base properties
            trainingProgram.setCreateDate(new Date());
            trainingProgram.setCreateBy("unknown");
            trainingProgram.setModifiedBy(null);
            trainingProgram.setModifiedDate(null);

            for (Long item: trainingProgramRequest.getSyllabusListId()){
                Syllabus syllabus = syllabusRepository.findById(item).orElse(null);
                Training_Syllabus training_syllabus = new Training_Syllabus();
                training_syllabus.setSyllabus(syllabus);
                training_syllabus.setTrainingProgram(trainingProgram);
                listTraining_Syllabus.add(training_syllabus);
            }

            trainingProgram.setTraining_syllabusList(listTraining_Syllabus);
            
            trainingProgramRepository.saveAndFlush(trainingProgram);

            apiResponse.ok(trainingProgram);
            return apiResponse;
        }catch (Exception ex){
            throw new ApplicationException();
        }
    }
}
