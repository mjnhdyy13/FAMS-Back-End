package com.example.hcm23_java14_team2.service;



import java.util.List;

import com.example.hcm23_java14_team2.model.response.UserPermission.UserPermissionResponse;

public interface UserPermissionService {
    List<UserPermissionResponse> getAllPermission();

    UserPermissionResponse findById(Long id);
}
