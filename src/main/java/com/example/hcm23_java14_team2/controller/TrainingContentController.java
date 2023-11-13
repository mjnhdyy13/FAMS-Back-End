package com.example.hcm23_java14_team2.controller;

import java.util.List;

import com.example.hcm23_java14_team2.model.entities.TrainingContent;
import com.example.hcm23_java14_team2.model.request.TrainingContent.TrainingContentRequest;

import com.example.hcm23_java14_team2.model.request.TrainingContent.TrainingContentUpdateRq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hcm23_java14_team2.exception.ApplicationException;
import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.model.response.ApiResponse;
import com.example.hcm23_java14_team2.model.response.TrainingContentResponse;
import com.example.hcm23_java14_team2.service.TrainingContentService;

import jakarta.validation.ValidationException;


@RestController
@RequestMapping("/api/v1/trainingContent")
public class TrainingContentController {
    @Autowired
    private TrainingContentService trainingContentService;

    @GetMapping("/list")
    public ResponseEntity<?> getAllTraningContent(){
        ApiResponse<List<TrainingContent>> response = new ApiResponse<List<TrainingContent>>();
        try {
            List<TrainingContent> trainingContentResponses = trainingContentService.getAll();

            response.ok(trainingContentResponses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        ApiResponse<TrainingContentResponse> response = new ApiResponse<TrainingContentResponse>();
        try {
            TrainingContentResponse trainingContentResponse = trainingContentService.getById(id);
            response.ok(trainingContentResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (NotFoundException nfe){
            response.notFound();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } 
        catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @PostMapping(value="add")
    public ResponseEntity<?> insert(@RequestBody TrainingContentRequest request) {
        ApiResponse<TrainingContentResponse> response = new ApiResponse<TrainingContentResponse>();
        try{
            TrainingContentResponse trainingContentResponse = trainingContentService.insertTrainingContent(request);
            response.ok(trainingContentResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ValidationException ex){
            throw new ValidationException();
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }
    @PatchMapping("/update")
    public ResponseEntity<?> updateTrainingContent(@RequestParam(value = "id",defaultValue = "") Long id,
                                                   @RequestBody TrainingContentUpdateRq request) {
        return new ResponseEntity<>(trainingContentService.updateTrainingContent(id,request),HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTrainingContent(@RequestParam(value = "id",defaultValue = "") Long id) {
        return new ResponseEntity<>(trainingContentService.deleteTrainingContent(id),HttpStatus.OK);
    }
}
