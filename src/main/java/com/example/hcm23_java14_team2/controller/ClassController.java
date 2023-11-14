package com.example.hcm23_java14_team2.controller;

import com.example.hcm23_java14_team2.exception.ApplicationException;
import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.model.entities.Class;
import com.example.hcm23_java14_team2.model.request.ClassRequest;
import com.example.hcm23_java14_team2.model.response.ApiResponse;
import com.example.hcm23_java14_team2.model.response.ClassDetailResponse;
import com.example.hcm23_java14_team2.model.response.SyllabusResponse;
import com.example.hcm23_java14_team2.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.hcm23_java14_team2.exception.ValidationException;
import com.example.hcm23_java14_team2.model.response.Api.ApiResponse;
import com.example.hcm23_java14_team2.model.response.Class.ClassResponse;
import com.example.hcm23_java14_team2.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/class")
public class ClassController {
    @Autowired
    private ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping("/search")
    public ResponseEntity<?> getByClassNamOrClassCode(@RequestBody ClassRequest request) {
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
    public ResponseEntity<?> findClassesByCalendar(@RequestBody ClassRequest request) {
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
