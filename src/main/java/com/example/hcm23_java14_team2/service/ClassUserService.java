package com.example.hcm23_java14_team2.service;

import com.example.hcm23_java14_team2.model.response.Api.ApiResponse;

import java.util.List;

public interface ClassUserService {
    ApiResponse<Object> addUserClass(Long idClass, List<Long> idUser);
    ApiResponse<Object> deleteUserClass(Long idClass, List<Long> idUser);
}
