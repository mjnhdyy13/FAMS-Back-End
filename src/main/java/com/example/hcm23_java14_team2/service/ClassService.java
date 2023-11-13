package com.example.hcm23_java14_team2.service;

import com.example.hcm23_java14_team2.model.request.ClassRequest;
import com.example.hcm23_java14_team2.model.response.ApiResponse;
import com.example.hcm23_java14_team2.model.response.ClassResponse;
import org.springframework.validation.BindingResult;

public interface ClassService {
    ClassResponse updateClass(Long id, ClassRequest classRequest, BindingResult bindingResult);

}
