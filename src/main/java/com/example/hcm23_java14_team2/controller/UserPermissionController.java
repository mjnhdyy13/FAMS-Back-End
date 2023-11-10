package com.example.hcm23_java14_team2.controller;


import com.example.hcm23_java14_team2.exception.ApplicationException;
import com.example.hcm23_java14_team2.model.response.ApiResponse;

import com.example.hcm23_java14_team2.model.response.UserPermissionResponse;
import com.example.hcm23_java14_team2.service.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-permission")
public class UserPermissionController {
    @Autowired
    private UserPermissionService userPermissionService;

    @GetMapping("/list")
    ResponseEntity<?> listPermission(){
        try{
            ApiResponse<List<UserPermissionResponse>> apiResponse = new ApiResponse<>();
            apiResponse.ok(userPermissionService.getAllPermission());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException("");
        }
    }
    
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to my website";
    }

}
