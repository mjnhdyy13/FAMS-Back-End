package com.example.hcm23_java14_team2.model.mapper;

import com.example.hcm23_java14_team2.model.entities.User;
import com.example.hcm23_java14_team2.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phone", target = "phone")
    //@Mapping(source = "user.dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "user.gender", target = "gender")
    @Mapping(source = "user.statusUser", target = "status")
    @Mapping(source = "user.createBy", target = "createBy")
    @Mapping(source = "user.createDate", target = "createDate")
    @Mapping(source = "user.modifiedBy", target = "modifiedBy")
    @Mapping(source = "user.modifiedDate", target = "modifiedDate")
    @Mapping(source = "userPermission.id", target = "role_id")
    UserResponse toResponse(User user);

    List<UserResponse> toUserListResponses(List<User> users);
}

