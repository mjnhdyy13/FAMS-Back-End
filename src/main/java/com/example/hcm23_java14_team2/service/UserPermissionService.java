package com.example.hcm23_java14_team2.service;



import com.example.hcm23_java14_team2.model.response.UserPermissionResponse;


import java.util.List;

public interface UserPermissionService {
    List<UserPermissionResponse> getAllPermission();

    UserPermissionResponse findById(Long id);
}
