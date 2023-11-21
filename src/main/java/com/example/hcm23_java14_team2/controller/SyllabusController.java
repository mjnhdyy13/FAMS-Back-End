package com.example.hcm23_java14_team2.controller;


import com.example.hcm23_java14_team2.exception.ApplicationException;
import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.helper.ExcelHelper;
import com.example.hcm23_java14_team2.model.request.Syllabus.SyllabusRequest;
import com.example.hcm23_java14_team2.model.response.Api.ApiResponse;
import com.example.hcm23_java14_team2.model.response.Syllabus.ImportMessageResponse;
import com.example.hcm23_java14_team2.model.response.Syllabus.SyllabusResponse;
import com.example.hcm23_java14_team2.model.response.Syllabus.UpdateSyllabusResponse;
import com.example.hcm23_java14_team2.service.SyllabusService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/syllabus")
public class SyllabusController {

    @Autowired
    private SyllabusService syllabusService;

    @PostMapping("/add")    
    public ResponseEntity<?> insertSyllabus(@RequestBody SyllabusRequest request) {
        try {
            return new ResponseEntity<>(syllabusService.insertSyllabus(request), HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }
    @GetMapping("/list")
    public ResponseEntity<?> getAllSyllabus(@RequestParam(value = "search",defaultValue = "") String search,
                                        @RequestParam(value = "page",required = false) Integer  page,
                                        @RequestParam(value = "size",defaultValue = "2") Integer  size) {
        try{
            if(page!= null)
                return new ResponseEntity<>(syllabusService.getAllSyllabusWithPage(search,page,size),HttpStatus.OK);
            return new ResponseEntity<>(syllabusService.getAllSyllabus(search),HttpStatus.OK);
        }catch (Exception e){
            throw new ApplicationException();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        try {
            ApiResponse<SyllabusResponse> apiResponse = new ApiResponse();
            apiResponse.ok(syllabusService.findById(id));
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex; // Rethrow NotFoundException
        } catch (Exception ex) {
            throw new ApplicationException(); // Handle other exceptions
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateSyllabus(@PathVariable  Long id,@Valid @RequestBody SyllabusRequest syllabusRequest, BindingResult bindingResult) {
        try {
            UpdateSyllabusResponse SyllabusResponse = syllabusService.updateSyllabus(id,syllabusRequest, bindingResult);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(SyllabusResponse);
            return ResponseEntity.ok(apiResponse);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }
    @DeleteMapping("/delete")
    ResponseEntity<?> delete(@RequestParam(value = "id") Long id){
        try{
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.ok(syllabusService.deleteSyllabus(id));
            return  new ResponseEntity<>(apiResponse,HttpStatus.OK);
        }catch (Exception e){
            throw new ApplicationException();
        }
    }
    @PostMapping(value = "/import", consumes = {"multipart/form-data"})
    public ResponseEntity<ImportMessageResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                syllabusService.importFile(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ImportMessageResponse(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ImportMessageResponse(message));
            }
        }

        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ImportMessageResponse(message));
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable  Long id) {
        String filename = "syllabus.xlsx";
        InputStreamResource file = new InputStreamResource(syllabusService.load(id));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

}