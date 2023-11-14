package com.example.hcm23_java14_team2.service.Impl;
import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.model.entities.*;
import com.example.hcm23_java14_team2.model.entities.Class;
import com.example.hcm23_java14_team2.model.entities.Enum.Role;
import com.example.hcm23_java14_team2.model.request.ClassRequest;
import com.example.hcm23_java14_team2.model.response.ClassDetailResponse;
import com.example.hcm23_java14_team2.model.response.SyllabusViewClassResponse;
import com.example.hcm23_java14_team2.model.response.TrainingProgramViewClassResponse;
import com.example.hcm23_java14_team2.exception.ApplicationException;
import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.exception.ValidationException;
import com.example.hcm23_java14_team2.model.entities.Class;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusClass;
import com.example.hcm23_java14_team2.model.mapper.ClassMapper;
import com.example.hcm23_java14_team2.model.response.Class.ClassResponse;
import com.example.hcm23_java14_team2.repository.ClassRepository;
import com.example.hcm23_java14_team2.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private ClassMapper classMapper;
    public ClassServiceImpl(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }
    @Override
    public List<Class> searchByClassName(ClassRequest request) {
        List<Class> classList = classRepository.searchByClassName(request);
        if (classList == null || classList.isEmpty()) {
            throw new NotFoundException("Class not found with ClassName Or ClassCode ");
        }
        return classList;
    }
    @Override
    public List<Class> findClasses(ClassRequest request) {
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
            if (Class.getStatus() == StatusClass.PLANNING || Class.getStatus() == StatusClass.SCHEDULED )
            {
                Class.setStatus(StatusClass.DEACTIVE);
                classRepository.saveAndFlush(Class);
                return classMapper.toResponse(Class);
            }else {
                throw new ValidationException("Class In Running");
            }

        } catch (ApplicationException ex) {
            throw ex;
        }
    }
}
