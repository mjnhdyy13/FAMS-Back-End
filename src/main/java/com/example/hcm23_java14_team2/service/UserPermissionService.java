package com.example.hcm23_java14_team2.service;



import java.util.List;

import com.example.hcm23_java14_team2.model.request.UserPermission.EditPermissionRequest;
import com.example.hcm23_java14_team2.model.request.UserPermission.ListEditUserPermissionRq;
import com.example.hcm23_java14_team2.model.response.Api.ApiResponse;
import com.example.hcm23_java14_team2.model.response.UserPermission.UserPermissionResponse;

public interface UserPermissionService {
    List<UserPermissionResponse> getAllPermission();

    UserPermissionResponse findById(Long id);

    ApiResponse<Object> editUserPermission(ListEditUserPermissionRq requests);
}
