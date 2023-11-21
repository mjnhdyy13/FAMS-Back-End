package com.example.hcm23_java14_team2.service.Impl;

import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.model.entities.*;
import com.example.hcm23_java14_team2.model.entities.Class;
import com.example.hcm23_java14_team2.model.entities.Enum.Role;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusClass;
import com.example.hcm23_java14_team2.model.request.Class.ClassRequest;
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
import com.example.hcm23_java14_team2.model.mapper.ClassMapper;

import com.example.hcm23_java14_team2.repository.*;
import com.example.hcm23_java14_team2.service.ClassService;
import com.example.hcm23_java14_team2.util.ValidatorUtil;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    ClassRepository classRepository;
    @Autowired
    UserRepository userRepository;
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
            //get account user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String fullName = userDetails.getUsername();
            User user = userRepository.findByGmail(fullName);

            Class existingClass = classRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Class Not Found"));

            TrainingProgram existingtrainingProgram = trainingProgramRepository.findById(classRequest.getTrainingProgramId())
                    .orElseThrow(() -> new NotFoundException("TrainingProgram Not Found"));

            Date date = new Date();
            if (existingClass.getStatus() == null || (existingClass.getStatus() != StatusClass.PLANNING && existingClass.getStatus() != StatusClass.SCHEDULED)) {
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
                existingClass.setStatus(classRequest.getStatus());
                existingClass.setFSU(classRequest.getFSU());
                existingClass.setModifiedBy(user.getName());
                existingClass.setModifiedDate(date);
                existingClass.setTrainingProgram(existingtrainingProgram);
                if (classRequest.getStatus() != null) {
                    existingClass.setStatus(classRequest.getStatus());
                }
            }
            classRepository.saveAndFlush(existingClass);

            ApiResponse<Object> apiResponse = new ApiResponse<>();
            ClassResponse classResponse = classMapper.toResponse(existingClass);
            classResponse.setModifiedDate(formatter.format(new Date()));
            classResponse.setCreateDate(formatter.format(existingClass.getCreateDate()));
            apiResponse.ok(classResponse);
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        classDetailResponse.setCreateBy(classDetail.getCreateBy());
        classDetailResponse.setCreateDate(formatter.format(classDetail.getCreateDate()));
        classDetailResponse.setModifiedBy(classDetail.getModifiedBy());
        classDetailResponse.setModifiedDate(formatter.format(classDetail.getModifiedDate()));
        classDetailResponse.setClassName(classDetail.getClassName());
        classDetailResponse.setClassCode(classDetail.getClassCode());
        classDetailResponse.setAttendee(classDetail.getAttendee());
        classDetailResponse.setStartTime(classDetail.getStartTime());
        classDetailResponse.setEndTime(classDetail.getEndTime());
        classDetailResponse.setLocation(classDetail.getLocation());
        classDetailResponse.setFsu(classDetail.getFSU());
        classDetailResponse.setStatus(classDetail.getStatus());
        List<String> trainers = new ArrayList<>();
        List<String> admins = new ArrayList<>();
        for (Class_User cu : classDetail.getClassUserList()) {
            User user = cu.getUser();
            Role role = user.getUserPermission().getRoleName();
            if (role == Role.TRAINER) {
                // this is the trainer
                trainers.add(user.getName());
            } else if (role == Role.CLASSADMIN) {
                // this is the admin
                admins.add(user.getName());
            }
        }
        classDetailResponse.setTrainerName(trainers);
        classDetailResponse.setAdminName(admins);

        TrainingProgram trainingProgram = classDetail.getTrainingProgram();
        if (trainingProgram != null) {
            TrainingProgramViewClassResponse trainingProgramViewClassResponse = convertTrainingProgramToDTO(trainingProgram);
            classDetailResponse.setTrainingProgram(trainingProgramViewClassResponse);
        }

        return classDetailResponse;
    }

    private TrainingProgramViewClassResponse convertTrainingProgramToDTO(TrainingProgram trainingProgram) {
        TrainingProgramViewClassResponse trainingProgramViewClassResponse = new TrainingProgramViewClassResponse();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        trainingProgramViewClassResponse.setName(trainingProgram.getName());
        trainingProgramViewClassResponse.setDuration(trainingProgram.getDuration());
        trainingProgramViewClassResponse.setModifiedBy(trainingProgram.getModifiedBy());
        trainingProgramViewClassResponse.setModifiedDate(formatter.format(trainingProgram.getModifiedDate()));
        List<SyllabusViewClassResponse> syllabusViewClassResponses = trainingProgram.getTraining_syllabusList().stream()
                .map(Training_Syllabus::getSyllabus)
                .map(this::convertSyllabusToDTO)
                .collect(Collectors.toList());

        trainingProgramViewClassResponse.setSyllabuses(syllabusViewClassResponses);

        return trainingProgramViewClassResponse;
    }

    private SyllabusViewClassResponse convertSyllabusToDTO(Syllabus syllabus) {
        SyllabusViewClassResponse syllabusViewClassResponse = new SyllabusViewClassResponse();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        syllabusViewClassResponse.setTopicName(syllabus.getTopicName());
        syllabusViewClassResponse.setCodeName(syllabus.getCodeName());
        syllabusViewClassResponse.setVersion(syllabus.getVersion());
        syllabusViewClassResponse.setStatus(syllabus.getStatus());
        syllabusViewClassResponse.setCreateBy(syllabus.getCreateBy());
        syllabusViewClassResponse.setCreateDate(formatter.format(syllabus.getCreateDate()));
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
    @Transactional
    public ApiResponse<Object> addClass(ClassRequest classRequest) {
        var Class = classMapper.toEntity(classRequest);
        TrainingProgram trainingProgram = trainingProgramRepository.findById(classRequest.getTrainingProgramId()).
                orElseThrow(() -> new NotFoundException("Không tìm thấy TrainingProgam"));

        Class.setTrainingProgram(trainingProgram);
        //Set end_day
        String inputDate = classRequest.getStartDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start_day = LocalDate.parse(inputDate, formatter);
        LocalDate end_day = start_day.plusDays(classRequest.getDuration());
        Class.setEndDate(end_day.toString());

        //Set create date
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        Class.setCreateDate(currentDate);

        //Set create by
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByGmail(userDetails.getUsername());
        Class.setCreateBy(user.getName());

        //Set Class Code
//        Arrays.stream(originalString.split("\\s+")) // neu lấy 3 kí tự của mỗi từ ghép lại
//                .filter(word -> word.length() >= 3)
//                .map(word -> word.substring(0, 3))
//                .forEach(result -> System.out.println("Ba ký tự đầu: " + result));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy");
        String lastTwoDigits = dateFormat.format(currentDate);
        String code = classRequest.getLocation()+"_"+Class.getClassName().replace(" ","")+"_"+lastTwoDigits;
        Class.setClassCode(code);
        //save
        var save = classRepository.saveAndFlush(Class);
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.ok(save);

        return apiResponse;
    }

    @Override
    public ApiResponse<Object> getAllClasses(String search) {
        var classes = classRepository.searchByName(search);
        classes.sort((class1, class2) -> class2.getModifiedDate().compareTo(class1.getModifiedDate()));
        List<ClassResponse> classResponses = new ArrayList<>();

        for (var item: classes){
            ClassResponse response = classMapper.toResponse(item);
            response.setCreateDate(formatter.format(item.getCreateDate()));
            response.setModifiedDate(formatter.format(item.getModifiedDate()));
            classResponses.add(response);
        }
        
        PageResponse<Object> apiResponse = new PageResponse<>();
        apiResponse.ok(classResponses);
        return apiResponse;
    }

    @Override
    public PageResponse<Object> getAllClassesWithPage(String searchName, Integer page, Integer size) {
        var PageClass = classRepository.searchByClassName(searchName, PageRequest.of(page-1,size));
        List<Class> classes = PageClass.getContent();
        List<ClassResponse> classResponses = new ArrayList<>();

        for (var item: classes){
            ClassResponse response = classMapper.toResponse(item);
            response.setCreateDate(formatter.format(item.getCreateDate()));
            response.setModifiedDate(formatter.format(item.getModifiedDate()));
            classResponses.add(response);
        }
        
        PageResponse<Object> pageResponse = new PageResponse<>();
        pageResponse.ok(classResponses);
        double total = Math.ceil((double)PageClass.getTotalElements() / size);
        pageResponse.setTotalPage(total);
        return pageResponse;
    }
}
