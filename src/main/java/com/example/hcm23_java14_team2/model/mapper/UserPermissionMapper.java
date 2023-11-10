package com.example.hcm23_java14_team2.model.mapper;

import com.example.hcm23_java14_team2.model.entities.UserPermission;
import com.example.hcm23_java14_team2.model.response.UserPermissionResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserPermissionMapper {
    
    UserPermissionResponse toResponse(UserPermission userPermission);

    List<UserPermissionResponse> toResponseList(List<UserPermission> userPermissionList);
}
