package com.example.hcm23_java14_team2.controller;

import java.util.List;

import com.example.hcm23_java14_team2.model.request.Syllabus.SyllabusRequest;
import com.example.hcm23_java14_team2.model.request.TrainingProgram.InsertTrainingProgramRequest;
import com.example.hcm23_java14_team2.model.request.TrainingProgram.UpdateTrainingProgramRequest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.hcm23_java14_team2.exception.ApplicationException;
import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.exception.ValidationException;
import com.example.hcm23_java14_team2.model.response.ApiResponse;
import com.example.hcm23_java14_team2.model.response.TrainingProgramResponse;
import com.example.hcm23_java14_team2.service.TrainingProgramService;

@RestController
@RequestMapping("/api/v1/trainingProgram")
public class TrainingProgramController {

    @Autowired
    private TrainingProgramService trainingProgramService;

    @GetMapping("/list")
    public ResponseEntity<?> getAllTrainingProgram(@RequestParam(value = "search",defaultValue = "") String search,
                                        @RequestParam(value = "page",required = false) Integer  page,
                                        @RequestParam(value = "size",defaultValue = "2") Integer  size) {
        try{
            if(page!= null)
                return new ResponseEntity<>(trainingProgramService.getAllTrainingProgramsWithPage(search,page,size),HttpStatus.OK);
            return new ResponseEntity<>(trainingProgramService.getAllTrainingPrograms(search),HttpStatus.OK);
        }catch (Exception e){
            throw new ApplicationException();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(Integer id) {
        ApiResponse<TrainingProgramResponse> apiResponse = new ApiResponse<TrainingProgramResponse>();
        try { 
            TrainingProgramResponse trainingProgramResponse = trainingProgramService.findById(id);
            if (trainingProgramResponse == null){
                apiResponse.notFound();
                return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
            }
            apiResponse.ok(trainingProgramResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }catch (NotFoundException ex){
            throw new NotFoundException(null);
        }catch (Exception e){
            throw new ApplicationException();
            }
    }

    @DeleteMapping(value = {"delete/{id}"})
    public ResponseEntity<?> deleteTraining(@PathVariable Integer id) {
        try {
            // Delete
            TrainingProgramResponse trainingProgramResponse = trainingProgramService.deleteTraining(id);

            // Response
            ApiResponse<TrainingProgramResponse> apiResponse = new ApiResponse<TrainingProgramResponse>();
            apiResponse.ok(trainingProgramResponse);
            return ResponseEntity.ok(apiResponse);
        } catch (NotFoundException ex) {
            throw ex; // Rethrow NotFoundException
        } catch (ValidationException ex) {
            throw ex; // Rethrow ValidationException
        } catch (Exception ex) {
            throw new ApplicationException(); // Handle other exceptions
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTrainingProgram(@PathVariable Integer id, @Valid @RequestBody UpdateTrainingProgramRequest trainingProgramRequest, BindingResult bindingResult) {
        try {
            TrainingProgramResponse trainingProgramResponse = trainingProgramService.updateTrainingProgram(id,trainingProgramRequest, bindingResult);
            ApiResponse<TrainingProgramResponse> apiResponse = new ApiResponse<TrainingProgramResponse>();
            apiResponse.ok(trainingProgramResponse);
            return ResponseEntity.ok(apiResponse);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (jakarta.validation.ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }
    @PostMapping("/add")
    public ResponseEntity<?> insertTraingProgram(@RequestBody InsertTrainingProgramRequest request) {
        try {
            return new ResponseEntity<>(trainingProgramService.insertTrainingProgram(request), HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (jakarta.validation.ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }
}
