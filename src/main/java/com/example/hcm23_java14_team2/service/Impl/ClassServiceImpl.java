package com.example.hcm23_java14_team2.service.Impl;
import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.model.entities.*;
import com.example.hcm23_java14_team2.model.entities.Class;
import com.example.hcm23_java14_team2.model.entities.Enum.Role;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusClass;
import com.example.hcm23_java14_team2.model.request.Class.ClassRequest;
import com.example.hcm23_java14_team2.model.request.Class.ClassSearchRequest;
import com.example.hcm23_java14_team2.model.response.ClassDetailResponse;
import com.example.hcm23_java14_team2.model.response.SyllabusViewClassResponse;
import com.example.hcm23_java14_team2.model.response.TrainingProgramViewClassResponse;
import com.example.hcm23_java14_team2.exception.ApplicationException;
import com.example.hcm23_java14_team2.exception.ValidationException;
import com.example.hcm23_java14_team2.model.entities.Syllabus;
import com.example.hcm23_java14_team2.model.entities.TrainingProgram;
import com.example.hcm23_java14_team2.model.entities.Training_Syllabus;
import com.example.hcm23_java14_team2.model.mapper.ClassMapper;
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

    public ClassServiceImpl(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Transactional
    @Override
    public ClassResponse updateClass(Long id, ClassRequest classRequest, BindingResult bindingResult) {
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
            return classMapper.toResponse(existingClass);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public List<Class> searchByClassName(ClassSearchRequest request) {
        List<Class> classList = classRepository.searchByClassName(request);
        if (classList == null || classList.isEmpty()) {
            throw new NotFoundException("Class not found with ClassName Or ClassCode ");
        }
        return classList;
    }

    @Override
    public List<Class> findClasses(ClassSearchRequest request) {
        List<Class> classList = classRepository.findClasses(request);
        if (classList == null || classList.isEmpty()) {
            throw new NotFoundException("Class not found");
        }
        return classList;
    }
    @Override
    public ClassDetailResponse getClassDetails(Long id) {
        Class aClass = classRepository.findById(id).orElseThrow(() -> new NotFoundException("Class not found with id " + id));
        return convertToDTO(aClass);
    }

    private ClassDetailResponse convertToDTO(Class aClass) {
        ClassDetailResponse classDetailResponse = new ClassDetailResponse();
        classDetailResponse.setCreateBy(aClass.getCreateBy());
        classDetailResponse.setCreateDate(String.valueOf(aClass.getCreateDate()));
        classDetailResponse.setModifiedBy(aClass.getModifiedBy());
        classDetailResponse.setModifiedDate(String.valueOf(aClass.getModifiedDate()));
        classDetailResponse.setClassName(aClass.getClassName());
        classDetailResponse.setClassCode(aClass.getClassCode());
        classDetailResponse.setAttendee(aClass.getAttendee());
        classDetailResponse.setStartTime(aClass.getStartTime());
        classDetailResponse.setEndTime(aClass.getEndTime());
        classDetailResponse.setLocation(aClass.getLocation());
        classDetailResponse.setFsu(aClass.getFSU());
        for (Class_User cu : aClass.getClassUserList()) {
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

        TrainingProgram trainingProgram = aClass.getTrainingProgram();
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
    public ClassResponse deleteByIdClass(Long id) {
        try {
            Class Class = classRepository.findById(id).orElse(null);
            if (Class == null) {
                throw new NotFoundException("Class Not Found");
            }
            // Update
            if (Class.getStatus() == StatusClass.PLANNING || Class.getStatus() == StatusClass.SCHEDULED) {
                Class.setStatus(StatusClass.DEACTIVE);
                classRepository.saveAndFlush(Class);
                return classMapper.toResponse(Class);
            } else {
                throw new ValidationException("Class In Running");
            }

        } catch (ApplicationException ex) {
            throw ex;
        }
    }
}
