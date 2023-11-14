package com.example.hcm23_java14_team2.service.Impl;

import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.model.entities.*;
import com.example.hcm23_java14_team2.model.entities.Class;
import com.example.hcm23_java14_team2.model.entities.Enum.Role;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusClass;
import com.example.hcm23_java14_team2.model.request.Class.ClassUpdateRequest;
import com.example.hcm23_java14_team2.model.request.Class.ClassSearchRequest;
import com.example.hcm23_java14_team2.model.response.PageResponse;
import com.example.hcm23_java14_team2.model.response.Api.ApiResponse;
import com.example.hcm23_java14_team2.model.response.Class.ClassDetailResponse;
import com.example.hcm23_java14_team2.model.response.Class.ClassResponse;
import com.example.hcm23_java14_team2.model.response.Class.SyllabusViewClassResponse;
import com.example.hcm23_java14_team2.model.response.Class.TrainingProgramViewClassResponse;
import com.example.hcm23_java14_team2.exception.ApplicationException;
import com.example.hcm23_java14_team2.exception.ValidationException;
import com.example.hcm23_java14_team2.handler.GlobalExceptionHandler;
import com.example.hcm23_java14_team2.model.mapper.ClassMapper;

import com.example.hcm23_java14_team2.repository.ClassRepository;
import com.example.hcm23_java14_team2.repository.SyllabusRepository;
import com.example.hcm23_java14_team2.repository.TrainingProgramRepository;
import com.example.hcm23_java14_team2.repository.Training_SyllabusRepository;
import com.example.hcm23_java14_team2.service.ClassService;
import com.example.hcm23_java14_team2.util.ValidatorUtil;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    ClassRepository classRepository;
    @Autowired
    Training_SyllabusRepository trainingSyllabusRepository;
    @Autowired
    TrainingProgramRepository trainingProgramRepository;
    @Autowired
    SyllabusRepository syllabusRepository;
    @Autowired
    ClassMapper classMapper;
    @Autowired
    private ValidatorUtil validatorUtil;

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @Transactional
    @Override
    public ApiResponse<Object> updateClass(Long id, ClassUpdateRequest classRequest, BindingResult bindingResult) {
        try {
            Class existingClass = classRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Class Not Found"));

            TrainingProgram existingtrainingProgram = trainingProgramRepository.findById(classRequest.getTrainingProgramId())
                    .orElseThrow(() -> new NotFoundException("TrainingProgram Not Found"));

            Date date = new Date();
            if (existingClass.getStatus() == null || (!existingClass.getStatus().toString().equals("PLANNING") && !existingClass.getStatus().toString().equals("SCHEDULED"))) {
                throw new NotFoundException("Class can't be edit!");
            } else {
                if (bindingResult.hasErrors()) {
                    Map<String, String> validationErrors = validatorUtil.toErrors(bindingResult.getFieldErrors());
                    throw new ValidationException(validationErrors);
                }
                existingClass.setClassName(classRequest.getClassName());
                existingClass.setClassCode(classRequest.getClassCode());
                existingClass.setEndDate(classRequest.getEndDate());
                existingClass.setStartDate(classRequest.getStartDate());
                existingClass.setStartTime(classRequest.getStartTime());
                existingClass.setEndTime(classRequest.getEndTime());
                existingClass.setLocation(classRequest.getLocation());
                existingClass.setDuration(classRequest.getDuration());
                existingClass.setAttendee(classRequest.getAttendee());
                existingClass.setFSU(classRequest.getFSU());
                existingClass.setModifiedBy(classRequest.getModifiedBy());
                existingClass.setModifiedDate(date);
                existingClass.setTrainingProgram(existingtrainingProgram);
                if (classRequest.getStatus() != null) {
                    existingClass.setStatus(classRequest.getStatus());
                }
            }
            classRepository.saveAndFlush(existingClass);

            ApiResponse<Object> apiResponse = new ApiResponse<>();
            ClassResponse classResponse = classMapper.toResponse(existingClass);
            classResponse.setModifiedDate(formatter.format(date));
            classResponse.setCreateDate(formatter.format(existingClass.getCreateDate()));
            apiResponse.ok(classMapper.toResponse(existingClass));
            return apiResponse;
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public ApiResponse<Object> findClasses(ClassSearchRequest request) {
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        List<Class> classList = classRepository.findClasses(request);
        if (classList == null || classList.isEmpty()) {
            throw new NotFoundException("Class not found");
        }
        apiResponse.ok(classMapper.toResponselist(classList));
        return apiResponse;
    }

    @Override
    public ApiResponse<Object> getClassDetails(Long id) {
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        Class classDetail = classRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Class not found with id " + id));
        ClassDetailResponse classDetailResponse = convertToDTO(classDetail);
        apiResponse.ok(classDetailResponse);
        return apiResponse;
    }

    private ClassDetailResponse convertToDTO(Class classDetail) {
        ClassDetailResponse classDetailResponse = new ClassDetailResponse();
        classDetailResponse.setCreateBy(classDetail.getCreateBy());
        classDetailResponse.setCreateDate(String.valueOf(classDetail.getCreateDate()));
        classDetailResponse.setModifiedBy(classDetail.getModifiedBy());
        classDetailResponse.setModifiedDate(String.valueOf(classDetail.getModifiedDate()));
        classDetailResponse.setClassName(classDetail.getClassName());
        classDetailResponse.setClassCode(classDetail.getClassCode());
        classDetailResponse.setAttendee(classDetail.getAttendee());
        classDetailResponse.setStartTime(classDetail.getStartTime());
        classDetailResponse.setEndTime(classDetail.getEndTime());
        classDetailResponse.setLocation(classDetail.getLocation());
        classDetailResponse.setFsu(classDetail.getFSU());
        for (Class_User cu : classDetail.getClassUserList()) {
            User user = cu.getUser();
            Role role = user.getUserPermission().getRoleName();
            if (role == Role.TRAINER) {
                // this is the trainer
                classDetailResponse.setTrainerName(user.getName());
            } else if (role == Role.CLASSADMIN) {
                // this is the admin
                classDetailResponse.setAdminName(user.getName());
            }
        }

        TrainingProgram trainingProgram = classDetail.getTrainingProgram();
        if (trainingProgram != null) {
            TrainingProgramViewClassResponse trainingProgramViewClassResponse = convertTrainingProgramToDTO(trainingProgram);
            classDetailResponse.setTrainingProgram(trainingProgramViewClassResponse);
        }

        return classDetailResponse;
    }

    private TrainingProgramViewClassResponse convertTrainingProgramToDTO(TrainingProgram trainingProgram) {
        TrainingProgramViewClassResponse trainingProgramViewClassResponse = new TrainingProgramViewClassResponse();
        trainingProgramViewClassResponse.setName(trainingProgram.getName());
        trainingProgramViewClassResponse.setDuration(trainingProgram.getDuration());
        trainingProgramViewClassResponse.setModifiedBy(trainingProgram.getModifiedBy());
        trainingProgramViewClassResponse.setModifiedDate(String.valueOf(trainingProgram.getModifiedDate()));
        List<SyllabusViewClassResponse> syllabusViewClassResponses = trainingProgram.getTraining_syllabusList().stream()
                .map(Training_Syllabus::getSyllabus)
                .map(this::convertSyllabusToDTO)
                .collect(Collectors.toList());

        trainingProgramViewClassResponse.setSyllabuses(syllabusViewClassResponses);

        return trainingProgramViewClassResponse;
    }

    private SyllabusViewClassResponse convertSyllabusToDTO(Syllabus syllabus) {
        SyllabusViewClassResponse syllabusViewClassResponse = new SyllabusViewClassResponse();
        syllabusViewClassResponse.setTopicName(syllabus.getTopicName());
        syllabusViewClassResponse.setCodeName(syllabus.getCodeName());
        syllabusViewClassResponse.setVersion(syllabus.getVersion());
        syllabusViewClassResponse.setStatus(syllabus.getStatus());
        syllabusViewClassResponse.setCreateBy(syllabus.getCreateBy());
        syllabusViewClassResponse.setCreateDate(String.valueOf(syllabus.getCreateDate()));
        // set other fields from syllabus to syllabusDTO
        return syllabusViewClassResponse;
    }

    @Override
    public ApiResponse<Object> deleteByIdClass(Long id) {
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        try {
            Class Class = classRepository.findById(id).orElse(null);
            if (Class == null) {
                throw new NotFoundException("Class Not Found");
            }
            // Update
            if (Class.getStatus() == StatusClass.PLANNING || Class.getStatus() == StatusClass.SCHEDULED) {
                Class.setStatus(StatusClass.DEACTIVE);
                classRepository.saveAndFlush(Class);
                ClassResponse classResponse = classMapper.toResponse(Class);
                apiResponse.ok(classResponse);
            } else {
                apiResponse.notFound("Not found class");
            }
            return apiResponse;
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public ApiResponse<Object> getAllClasses(String search) {
        var classList = classRepository.searchByName(search);
        List<ClassResponse> classResponses = classMapper.toResponselist(classList);

        for (var item: classResponses){
            item.setCreateDate(formatter.format(item.getCreateDate()));
            item.setModifiedDate(formatter.format(item.getModifiedDate()));
        }
        
        PageResponse<Object> apiResponse = new PageResponse<>();
        apiResponse.ok(classResponses);
        return apiResponse;
    }

    @Override
    public PageResponse<Object> getAllClassesWithPage(String searchName, Integer page, Integer size) {
        var PageClass = classRepository.searchByClassName(searchName, PageRequest.of(page-1,size));
        List<ClassResponse> classResponses = classMapper.toResponselist(PageClass.getContent());

        for (var item: classResponses){
            item.setCreateDate(formatter.format(item.getCreateDate()));
            item.setModifiedDate(formatter.format(item.getModifiedDate()));
        }
        
        PageResponse<Object> pageResponse = new PageResponse<>();
        pageResponse.ok(classResponses);
        double total = Math.ceil((double)PageClass.getTotalElements() / size);
        pageResponse.setTotalPage(total);
        return pageResponse;
    }
}
