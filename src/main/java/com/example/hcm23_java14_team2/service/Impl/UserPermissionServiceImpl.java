package com.example.hcm23_java14_team2.service.Impl;


import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.model.entities.UserPermission;
import com.example.hcm23_java14_team2.model.mapper.UserPermissionMapper;
import com.example.hcm23_java14_team2.model.request.UserPermission.EditPermissionRequest;
import com.example.hcm23_java14_team2.model.response.Api.ApiResponse;
import com.example.hcm23_java14_team2.model.response.UserPermission.UserPermissionResponse;
import com.example.hcm23_java14_team2.repository.UserPermissionRepository;
import com.example.hcm23_java14_team2.service.UserPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserPermissionServiceImpl implements UserPermissionService {

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    private final UserPermissionMapper userPermissionMapper;
    @Override
    public List<UserPermissionResponse> getAllPermission() {

        return  userPermissionMapper.toResponseList(userPermissionRepository.findAll());
    }

    @Override
    public UserPermissionResponse findById(Long id) {
        UserPermission userPermission = userPermissionRepository.findById(id).orElse(null);
        if(userPermission == null) {throw  new NotFoundException("");}
        return  userPermissionMapper.toResponse(userPermissionRepository.findById(id).get());
    }

    @Override
    public ApiResponse<Object> editUserPermission(List<EditPermissionRequest> requests) {
        try {
            for (EditPermissionRequest editPermissionRequest : requests) {
                var user_permission = userPermissionRepository.findById(editPermissionRequest.getId());
                if (user_permission.isPresent()) {
                    user_permission.get().setSyllabus(editPermissionRequest.getSyllabus());
                    user_permission.get().setTrainingProgram(editPermissionRequest.getTrainingProgram());
                    user_permission.get().setClassRoom(editPermissionRequest.getClassRoom());
                    user_permission.get().setLearningMaterial(editPermissionRequest.getLearningMaterial());
                    userPermissionRepository.save(user_permission.get());
                } else {
                    return ApiResponse.builder()
                            .statusCode("400")
                            .message("Not found user permission")
                            .build();
                }
                return ApiResponse.builder()
                        .statusCode("200")
                        .message("Change user permission successfully!")
                        .build();
            }
        } catch (Exception e) {
            return ApiResponse.builder()
                    .statusCode("400")
                    .message(e.getMessage())
                    .build();
        }
        return ApiResponse.builder()
                .statusCode("400")
                .message("Has a failed, check the input")
                .build();
    }
}

