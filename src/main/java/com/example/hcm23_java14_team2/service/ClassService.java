package com.example.hcm23_java14_team2.service;

import com.example.hcm23_java14_team2.model.response.Class.ClassResponse;

public interface ClassService {
    ClassResponse updateClass(Long id, ClassRequest classRequest, BindingResult bindingResult);
    List<Class> searchByClassName(ClassSearchRequest request);
    List<Class> findClasses(ClassSearchRequest request);
    ClassDetailResponse getClassDetails(Long id);
    ClassResponse deleteByIdClass(Long id);
}
