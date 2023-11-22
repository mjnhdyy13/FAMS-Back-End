package com.example.hcm23_java14_team2.controller;

import com.example.hcm23_java14_team2.exception.ApplicationException;
import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.exception.ValidationException;
import com.example.hcm23_java14_team2.model.entities.Class_User;
import com.example.hcm23_java14_team2.model.request.Class.ClassRequest;
import com.example.hcm23_java14_team2.model.response.Api.ApiResponse;
import com.example.hcm23_java14_team2.service.ClassService;
import com.example.hcm23_java14_team2.service.ClassUserService;
import com.example.hcm23_java14_team2.service.TrainingSyllabusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.hcm23_java14_team2.model.entities.Class;
import com.example.hcm23_java14_team2.model.request.Training_SyllabusRequest;
import com.example.hcm23_java14_team2.model.request.Class.ClassSearchRequest;
import com.example.hcm23_java14_team2.model.request.Class.ClassUpdateRequest;

import java.util.List;

@RestController
@RequestMapping("/api/v1/class")
public class ClassController {

    @Autowired
    private ClassService classService;
    @Autowired
    private TrainingSyllabusService trainingSyllabusService;
    @Autowired
    private ClassUserService classUserService;
    public ClassController(ClassService classService) {
        this.classService = classService;
    }
    @GetMapping("/list")
    public ResponseEntity<?> getAllClasses(@RequestParam(value = "search",defaultValue = "") String search,
                                           @RequestParam(value = "page",required = false) Integer page,
                                           @RequestParam(value = "size",defaultValue = "2") Integer size) {
        try {
            if (page != null)
                return new ResponseEntity<>(classService.getAllClassesWithPage(search, page, size), HttpStatus.OK);
            return new ResponseEntity<>(classService.getAllClasses(search), HttpStatus.OK);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateClass(@PathVariable Long id, @Valid @RequestBody ClassUpdateRequest classRequest, BindingResult bindingResult) {
        try {
            ApiResponse<Object> apiResponse = classService.updateClass(id,classRequest, bindingResult);
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
            ApiResponse<Object> apiResponse = trainingSyllabusService.deleteTrainingSyllabus(trainingSyllabusRequest.getTrainingProgram(), trainingSyllabusRequest.getSyllabus(), bindingResult);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
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

    @DeleteMapping(value = {"delete/{id}"})
    public ResponseEntity<?> deleteTraining(@PathVariable Long id) {
        try {
            // Delete
            ApiResponse<Object> apiResponse = classService.deleteByIdClass(id);

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex; // Rethrow NotFoundException
        } catch (ValidationException ex) {
            throw ex; // Rethrow ValidationException
        }
    }
    @PostMapping("/search_by_calendar")
    public ResponseEntity<?> findClassesByCalendar(@RequestBody ClassSearchRequest request) {
        try{
            ApiResponse<Object> apiResponse = classService.findClasses(request);
            apiResponse.ok();
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
//        try{
            ApiResponse<Object> apiResponse = classService.getClassDetails(id);

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
//        } catch (NotFoundException ex) {
//            throw ex; // Rethrow NotFoundException
//        } catch (Exception ex) {
//            throw new ApplicationException(); // Handle other exceptions
//        }

    }
    @PostMapping("{id}/addUser")
    public ResponseEntity<?> addClassUser(@PathVariable Long id, @Valid @RequestBody List<Long> idUser){
        try{
            return new ResponseEntity<>(classUserService.addUserClass(id,idUser), HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex; // Rethrow NotFoundException
        } catch (Exception ex) {
            throw new ApplicationException(); // Handle other exceptions
        }
    }
    @DeleteMapping("{id}/deleteUser")
    public ResponseEntity<?> deleteClassUser(@PathVariable Long id, @Valid @RequestBody List<Long> idUser){
        try{
            return new ResponseEntity<>(classUserService.deleteUserClass(id,idUser), HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex; // Rethrow NotFoundException
        } catch (Exception ex) {
            throw new ApplicationException(); // Handle other exceptions
        }
    }
    @PostMapping("/create")
    public  ResponseEntity<?> createClass(@RequestBody ClassRequest classRequest){
        try{
            return new ResponseEntity<>(classService.addClass(classRequest), HttpStatus.OK);
        }catch (ApplicationException e){
            throw new ApplicationException();
        }
    }
}
