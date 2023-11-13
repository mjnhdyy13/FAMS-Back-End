package com.example.hcm23_java14_team2.controller;


import com.example.hcm23_java14_team2.exception.ApplicationException;
import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.model.request.ClassRequest;
import com.example.hcm23_java14_team2.model.request.Training_SyllabusRequest;
import com.example.hcm23_java14_team2.model.response.ApiResponse;
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

@RestController
@RequestMapping("/api/v1/class")
public class ClassController {

    @Autowired
    private ClassService classService;
    @Autowired
    private TrainingSyllabusService trainingSyllabusService;

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
    public ResponseEntity<?> addTrainingSyllabus(@Valid @RequestBody Training_SyllabusRequest trainingSyllabusRequest, BindingResult bindingResult){
        try{
            return new ResponseEntity<>(trainingSyllabusService.addTrainingSyllabus(trainingSyllabusRequest.getTrainingProgram(), trainingSyllabusRequest.getSyllabus(), bindingResult), HttpStatus.OK);
        }
        catch (Exception e){
            throw new ApplicationException();
        }
    }
}
