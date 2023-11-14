package com.example.hcm23_java14_team2.service;

<<<<<<< HEAD
import org.springframework.validation.BindingResult;

import com.example.hcm23_java14_team2.model.request.Class.ClassUpdateRequest;
import com.example.hcm23_java14_team2.model.request.Class.ClassSearchRequest;
import com.example.hcm23_java14_team2.model.response.PageResponse;
import com.example.hcm23_java14_team2.model.response.Api.ApiResponse;
import com.example.hcm23_java14_team2.model.response.Class.ClassResponse;

=======
import com.example.hcm23_java14_team2.model.request.ClassRequest;
import com.example.hcm23_java14_team2.model.response.ClassDetailResponse;
import com.example.hcm23_java14_team2.model.response.ClassResponse;

import java.util.List;

>>>>>>> 1b3faff1242c8ab2ac56f20e7ae290f37f18466e
public interface ClassService {
    PageResponse<Object> getAllClassesWithPage(String searchName, Integer page, Integer size);
    ApiResponse<Object> getAllClasses(String search);
    ApiResponse<Object> findClasses(ClassSearchRequest request);

    ApiResponse<Object> updateClass(Long id, ClassUpdateRequest classRequest, BindingResult bindingResult);
    ApiResponse<Object> getClassDetails(Long id);
    ApiResponse<Object> deleteByIdClass(Long id);
}
