package com.example.hcm23_java14_team2.service;

import com.example.hcm23_java14_team2.model.entities.User;
import com.example.hcm23_java14_team2.model.request.User.AddUserRequest;
import com.example.hcm23_java14_team2.model.request.User.UserRequest;
import com.example.hcm23_java14_team2.model.response.ApiResponse;
import com.example.hcm23_java14_team2.model.response.PageResponse;
import com.example.hcm23_java14_team2.model.response.UserResponse;
import org.springframework.validation.BindingResult;
import java.util.List;

public interface UserService {
    PageResponse<List<UserResponse>> getAllUserWithPage(String search, Integer page, Integer size);
    ApiResponse<List<UserResponse>> getAllUser(String search);
    UserResponse findById(Long id);
    UserResponse updateUser(Long id,UserRequest userRequest, BindingResult bindingResult);
    UserResponse deleteById(Long id);

    List<UserResponse> getUserListofPage(int page,int size);

    long getTotalPage(long total, int size);
    int enableUser(String email);

    User findByEmail(String email);

    ApiResponse<Object> addUser(AddUserRequest request);
}