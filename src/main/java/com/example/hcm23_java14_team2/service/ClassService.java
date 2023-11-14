package com.example.hcm23_java14_team2.service;

import com.example.hcm23_java14_team2.model.request.ClassRequest;
import com.example.hcm23_java14_team2.model.response.ClassDetailResponse;
import com.example.hcm23_java14_team2.model.response.ClassResponse;

import java.util.List;

public interface ClassService {
    List<Class> searchByClassName(ClassRequest request);
    List<Class> findClasses(ClassRequest request);
    ClassDetailResponse getClassDetails(Long id);

    ClassResponse deleteByIdClass(Long id);
}
