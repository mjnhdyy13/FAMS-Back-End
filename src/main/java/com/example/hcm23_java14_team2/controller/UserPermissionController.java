package com.example.hcm23_java14_team2.controller;


import com.example.hcm23_java14_team2.exception.ApplicationException;
import com.example.hcm23_java14_team2.model.request.UserPermission.EditPermissionRequest;
import com.example.hcm23_java14_team2.model.request.UserPermission.ListEditUserPermissionRq;
import com.example.hcm23_java14_team2.model.response.Api.ApiResponse;
import com.example.hcm23_java14_team2.model.response.UserPermission.UserPermissionResponse;
import com.example.hcm23_java14_team2.service.UserPermissionService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/edit")
    ResponseEntity<?> editUserPermission(@RequestBody ListEditUserPermissionRq requests){
        return new ResponseEntity<>(userPermissionService.editUserPermission(requests),HttpStatus.OK);
    }
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to my website";
    }

}
