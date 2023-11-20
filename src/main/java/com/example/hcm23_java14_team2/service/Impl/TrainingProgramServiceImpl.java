package com.example.hcm23_java14_team2.service.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.hcm23_java14_team2.exception.ValidationException;
import com.example.hcm23_java14_team2.model.entities.User;
import com.example.hcm23_java14_team2.model.response.PageResponse;
import com.example.hcm23_java14_team2.repository.UserRepository;
import com.example.hcm23_java14_team2.util.ValidatorUtil;
import com.example.hcm23_java14_team2.validator.TrainingProgramValidator;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.hcm23_java14_team2.exception.ApplicationException;
import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.model.entities.Syllabus;
import com.example.hcm23_java14_team2.model.entities.TrainingProgram;
import com.example.hcm23_java14_team2.model.entities.Training_Syllabus;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusTrainingProgram;
import com.example.hcm23_java14_team2.model.mapper.TrainingProgramMapper;
import com.example.hcm23_java14_team2.model.request.TrainingProgram.InsertTrainingProgramRequest;
import com.example.hcm23_java14_team2.model.request.TrainingProgram.UpdateTrainingProgramRequest;
import com.example.hcm23_java14_team2.model.response.Api.ApiResponse;
import com.example.hcm23_java14_team2.model.response.TrainingProgram.InsertTrainingProgramResponse;
import com.example.hcm23_java14_team2.model.response.TrainingProgram.UpdateTrainingProgramResponse;
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

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public PageResponse<List<UpdateTrainingProgramResponse>> getAllTrainingProgramsWithPage(String search, Integer page, Integer size){
        Page<TrainingProgram> t = trainingProgramRepository.searchByNameWithPage(search,PageRequest.of(page-1,size));
        List<TrainingProgram> PageTraining = t.getContent();
        List<UpdateTrainingProgramResponse> trainingProgramResponses = new ArrayList<>();

        for(TrainingProgram item : PageTraining){
            var trainingProgramResponse = trainingProgramMapper.toUpdateResponse(item);
            //check if some properties is null then skip
            if (item.getCreateDate() != null){
                 trainingProgramResponse.setCreateDate(formatter.format(item.getCreateDate()));
            }
            if (item.getModifiedDate() != null){
                trainingProgramResponse.setModifiedDate(formatter.format(item.getModifiedDate()));
            }

            // get syllabus list for each training program
            trainingProgramResponse.setSyllabusList(getSyllabusList(item));
           
            // trainingProgramResponse.setUserNameCreate(item.getUser().getName());
            trainingProgramResponses.add(trainingProgramResponse);
        }

        PageResponse<List<UpdateTrainingProgramResponse>> listPageResponse = new PageResponse<>();
        listPageResponse.ok(trainingProgramResponses);
        double total = Math.ceil((double)t.getTotalElements() / size);
        listPageResponse.setTotalPage(total);
        return  listPageResponse;

    }


    @Override
    public UpdateTrainingProgramResponse findById(Integer id) {
        TrainingProgram trainingProgram = trainingProgramRepository.findById(id).orElse(null);

        if (trainingProgram == null){
            return null;
        }
        UpdateTrainingProgramResponse trainingProgramResponse = trainingProgramMapper.toUpdateResponse(trainingProgram);
        if (trainingProgram.getUser() != null){
                trainingProgramResponse.setCreateBy(trainingProgram.getUser().getName());
        }
        if (trainingProgram.getCreateDate() != null){
                 trainingProgramResponse.setCreateDate(formatter.format(trainingProgram.getCreateDate()));
        }
        
        trainingProgramResponse.setSyllabusList(getSyllabusList(trainingProgram));
        return trainingProgramResponse;
    }

    @Override
    public ApiResponse<List<InsertTrainingProgramResponse>> getAllTrainingPrograms(String search) {
        List<TrainingProgram> trainingPrograms = trainingProgramRepository.searchByName(search);
        List<InsertTrainingProgramResponse> trainingProgramResponses = new ArrayList<>();

        //set base properties
        for (TrainingProgram item : trainingPrograms){
            var trainingProgramResponse = trainingProgramMapper.toResponse(item);
            trainingProgramResponse.setCreateDate(formatter.format(item.getCreateDate()));
            trainingProgramResponses.add(trainingProgramResponse);
        }
        ApiResponse<List<InsertTrainingProgramResponse>> apiResponse = new ApiResponse<>();
        apiResponse.ok(trainingProgramResponses);
        return apiResponse;
    }
    
    @Override
    public ApiResponse<InsertTrainingProgramResponse> deleteTraining(Integer id){
        ApiResponse<InsertTrainingProgramResponse> apiResponse = new ApiResponse<>();
        try {
            TrainingProgram trainingProgram = trainingProgramRepository.findById(id).orElse(null);
            if (trainingProgram == null) {
                throw new NotFoundException("TrainingProgram Not Found");
            }
            // Update
            trainingProgram.setStatus(StatusTrainingProgram.valueOf("INACTIVE"));
            trainingProgramRepository.saveAndFlush(trainingProgram);

            apiResponse.ok(trainingProgramMapper.toResponse(trainingProgram));
            return apiResponse;
        } catch (ApplicationException ex) {
            throw ex;
        }
    }
    @Transactional
    public ApiResponse<UpdateTrainingProgramResponse> updateTrainingProgram(Integer id, UpdateTrainingProgramRequest trainingProgramRequest, BindingResult bindingResult) {
        ApiResponse<UpdateTrainingProgramResponse> apiResponse = new ApiResponse<>();

        //get account user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String fullName = userDetails.getUsername();

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
            existingTrainingProgram.setModifiedBy(fullName);

            existingTrainingProgram.setModifiedDate(new Date());

            trainingProgramRepository.removeSyllabusList(existingTrainingProgram.getId());
            existingTrainingProgram.setTraining_syllabusList(getTraining_SyllabusList(trainingProgramRequest.getSyllabusListId(), existingTrainingProgram));
            trainingProgramRepository.saveAndFlush(existingTrainingProgram);
            
            UpdateTrainingProgramResponse trainingProgramResponse = trainingProgramMapper.toUpdateResponse(existingTrainingProgram);
            trainingProgramResponse.setSyllabusList(getSyllabusList(existingTrainingProgram));
            trainingProgramResponse.setModifiedDate(formatter.format(existingTrainingProgram.getModifiedDate()));
            trainingProgramResponse.setCreateDate(formatter.format(existingTrainingProgram.getCreateDate()));
            apiResponse.ok(trainingProgramResponse);
            return apiResponse;
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public ApiResponse<Object> insertTrainingProgram(InsertTrainingProgramRequest trainingProgramRequest) {
        ApiResponse<Object> apiResponse = new ApiResponse<>();

        //get account user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String fullName = userDetails.getUsername();
        User user = userRepository.findByGmail(fullName);
        try{
            List<Training_Syllabus> listTraining_Syllabus = new ArrayList<>();

            TrainingProgram trainingProgram = TrainingProgram.builder()
                .name(trainingProgramRequest.getName())
                .status(StatusTrainingProgram.DRAFT)
                .code(trainingProgramRequest.getCode())
                .startTime(trainingProgramRequest.getStartTime())
                .duration(trainingProgramRequest.getDuration())
                .user(user)
                .build();

            
            trainingProgram.setCreateDate(new Date());
            trainingProgram.setCreateBy(user.getName());
            trainingProgram.setModifiedBy(user.getName());
            trainingProgram.setModifiedDate(new Date());

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

    private List<Syllabus> getSyllabusList(TrainingProgram trainingProgram){
        List<Syllabus> syllabusList = new ArrayList<>();
        for (Training_Syllabus item: trainingProgram.getTraining_syllabusList()){
            syllabusList.add(item.getSyllabus());
        }
        return syllabusList;
    }

    private List<Training_Syllabus> getTraining_SyllabusList(List<Long> syllabusListId, TrainingProgram trainingProgram){
        List<Training_Syllabus> training_syllabusList = new ArrayList<>();
        for (Long id: syllabusListId){
            Training_Syllabus training_syllabus = new Training_Syllabus();
            Syllabus syllabus = syllabusRepository.findById(id).orElse(null);
            if (syllabus != null){
                training_syllabus.setSyllabus(syllabus);
                training_syllabus.setTrainingProgram(trainingProgram);
                training_syllabusList.add(training_syllabus);
            }
        }
        return training_syllabusList;
        
    }
}
