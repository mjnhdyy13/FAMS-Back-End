package com.example.hcm23_java14_team2.model.mapper;


import org.mapstruct.Mapper;

import com.example.hcm23_java14_team2.model.entities.UserPermission;
import com.example.hcm23_java14_team2.model.response.UserPermission.UserPermissionResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserPermissionMapper {
    
    UserPermissionResponse toResponse(UserPermission userPermission);

    List<UserPermissionResponse> toResponseList(List<UserPermission> userPermissionList);
}
