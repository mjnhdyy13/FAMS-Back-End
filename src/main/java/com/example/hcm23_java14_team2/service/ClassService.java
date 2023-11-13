package com.example.hcm23_java14_team2.service;

import com.example.hcm23_java14_team2.model.request.Class.ClassRequest;
import com.example.hcm23_java14_team2.model.request.Class.ClassSearchRequest;
import com.example.hcm23_java14_team2.model.response.ClassDetailResponse;
import com.example.hcm23_java14_team2.model.response.ClassResponse;
import org.springframework.validation.BindingResult;
import com.example.hcm23_java14_team2.model.entities.Class;
import java.util.List;

public interface ClassService {
    ClassResponse updateClass(Long id, ClassRequest classRequest, BindingResult bindingResult);
    List<Class> searchByClassName(ClassSearchRequest request);
    List<Class> findClasses(ClassSearchRequest request);
    ClassDetailResponse getClassDetails(Long id);
    ClassResponse deleteByIdClass(Long id);
}
