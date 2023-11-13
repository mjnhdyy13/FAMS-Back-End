package com.example.hcm23_java14_team2.service.Impl;

import com.example.hcm23_java14_team2.exception.ApplicationException;
import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.exception.ValidationException;
import com.example.hcm23_java14_team2.model.entities.Class;
import com.example.hcm23_java14_team2.model.entities.Syllabus;
import com.example.hcm23_java14_team2.model.entities.TrainingProgram;
import com.example.hcm23_java14_team2.model.entities.Training_Syllabus;
import com.example.hcm23_java14_team2.model.mapper.ClassMapper;
import com.example.hcm23_java14_team2.model.request.ClassRequest;
import com.example.hcm23_java14_team2.model.response.ApiResponse;
import com.example.hcm23_java14_team2.model.response.ClassResponse;
import com.example.hcm23_java14_team2.repository.ClassRepository;
import com.example.hcm23_java14_team2.repository.SyllabusRepository;
import com.example.hcm23_java14_team2.repository.TrainingProgramRepository;
import com.example.hcm23_java14_team2.repository.Training_SyllabusRepository;
import com.example.hcm23_java14_team2.service.ClassService;
import com.example.hcm23_java14_team2.util.ValidatorUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

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

    @Transactional
    @Override
    public ClassResponse updateClass(Long id, ClassRequest classRequest, BindingResult bindingResult) {
        try{
            Class existingClass = classRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Class Not Found"));

            TrainingProgram existingtrainingProgram = trainingProgramRepository.findById(classRequest.getTrainingProgramId())
                    .orElseThrow(() -> new NotFoundException("TrainingProgram Not Found"));

            Date date = new Date();
            if (existingClass.getStatus()==null || (!existingClass.getStatus().toString().equals("PLANNING") && !existingClass.getStatus().toString().equals("SCHEDULED"))){
                throw new NotFoundException("Class can't be edit!");
            }
            else {
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
            return classMapper.toResponse(existingClass);
        }
        catch (ApplicationException ex) {
            throw ex;
        }
    }

}
