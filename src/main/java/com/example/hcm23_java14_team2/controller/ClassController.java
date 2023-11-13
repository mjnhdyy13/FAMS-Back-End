package com.example.hcm23_java14_team2.controller;

import com.example.hcm23_java14_team2.exception.ApplicationException;
import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.model.request.Class.ClassRequest;
import com.example.hcm23_java14_team2.model.request.Class.ClassSearchRequest;
import com.example.hcm23_java14_team2.model.request.Training_SyllabusRequest;
import com.example.hcm23_java14_team2.model.response.ApiResponse;
import com.example.hcm23_java14_team2.model.response.ClassDetailResponse;
import com.example.hcm23_java14_team2.model.response.ClassResponse;
import com.example.hcm23_java14_team2.service.ClassService;
import com.example.hcm23_java14_team2.service.TrainingSyllabusService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.hcm23_java14_team2.model.entities.Class;
import java.util.List;

@RestController
@RequestMapping("/api/v1/class")
public class ClassController {

    @Autowired
    private ClassService classService;
    @Autowired
    private TrainingSyllabusService trainingSyllabusService;
    public ClassController(ClassService classService) {
        this.classService = classService;
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateClass(@PathVariable  Long id,@Valid @RequestBody ClassRequest classRequest, BindingResult bindingResult) {
        try {
            ClassResponse classResponse = classService.updateClass(id,classRequest, bindingResult);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(classResponse);
            return ResponseEntity.ok(apiResponse);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }
    @DeleteMapping("/training_syllabus")
    public ResponseEntity<?> deleteTrainingSyllabus(@Valid @RequestBody Training_SyllabusRequest trainingSyllabusRequest, BindingResult bindingResult){
        try{
            return new ResponseEntity<>(trainingSyllabusService.deleteTrainingSyllabus(trainingSyllabusRequest.getTrainingProgram(), trainingSyllabusRequest.getSyllabus(), bindingResult), HttpStatus.OK);
        }
        catch (Exception e){
            throw new ApplicationException();
        }
    }
    @PostMapping("/training_syllabus")
    public ResponseEntity<?> addTrainingSyllabus(@Valid @RequestBody Training_SyllabusRequest trainingSyllabusRequest, BindingResult bindingResult) {
        try {
            return new ResponseEntity<>(trainingSyllabusService.addTrainingSyllabus(trainingSyllabusRequest.getTrainingProgram(), trainingSyllabusRequest.getSyllabus(), bindingResult), HttpStatus.OK);
        } catch (Exception e) {
            throw new ApplicationException();
        }
    }
    @PostMapping("/search")
    public ResponseEntity<?> getByClassNamOrClassCode(@RequestBody ClassSearchRequest request) {
        try {
            ApiResponse<List<Class>> apiResponse = new ApiResponse();
            apiResponse.ok(classService.searchByClassName(request));
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (NotFoundException ex) {
            throw ex; // Rethrow NotFoundException
        } catch (Exception ex) {
            throw new ApplicationException(); // Handle other exceptions
        }
    }
    @DeleteMapping(value = {"delete/{id}"})
    public ResponseEntity<?> deleteTraining(@PathVariable Long id) {
        try {
            // Delete
            ClassResponse classResponse = classService.deleteByIdClass(id);
            ApiResponse<ClassResponse> apiResponse = new ApiResponse<ClassResponse>();
            apiResponse.ok(classResponse);
            return ResponseEntity.ok(apiResponse);
        } catch (NotFoundException ex) {
            throw ex; // Rethrow NotFoundException
        } catch (ValidationException ex) {
            throw ex; // Rethrow ValidationException
        }
    }
    @PostMapping("/search_by_calendar")
    public ResponseEntity<?> findClassesByCalendar(@RequestBody ClassSearchRequest request) {
        try{
            ApiResponse<List<Class>> apiResponse = new ApiResponse();
            apiResponse.ok(classService.findClasses(request));
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex; // Rethrow NotFoundException
        } catch (Exception ex) {
            throw new ApplicationException(); // Handle other exceptions
        }
    }
    @GetMapping("/classes/{id}")
    public ResponseEntity<?> getClassDetails(@PathVariable Long id)
    {
        try{
            ApiResponse<ClassDetailResponse> apiResponse = new ApiResponse();
            apiResponse.ok(classService.getClassDetails(id));
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex; // Rethrow NotFoundException
        } catch (Exception ex) {
            throw new ApplicationException(); // Handle other exceptions
        }

    }
}
