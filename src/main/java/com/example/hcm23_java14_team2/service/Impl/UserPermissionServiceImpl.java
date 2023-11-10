package com.example.hcm23_java14_team2.service.Impl;


import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.model.entities.UserPermission;
import com.example.hcm23_java14_team2.model.mapper.UserPermissionMapper;
import com.example.hcm23_java14_team2.model.response.UserPermissionResponse;
import com.example.hcm23_java14_team2.repository.UserPermissionRepository;
import com.example.hcm23_java14_team2.service.UserPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserPermissionServiceImpl implements UserPermissionService {

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    private final UserPermissionMapper userPermissionMapper;
    @Override
    public List<UserPermissionResponse> getAllPermission() {

        return  userPermissionMapper.toResponseList(userPermissionRepository.findAll());
    }

    @Override
    public UserPermissionResponse findById(Long id) {
        UserPermission userPermission = userPermissionRepository.findById(id).orElse(null);
        if(userPermission == null) {throw  new NotFoundException("");}
        return  userPermissionMapper.toResponse(userPermissionRepository.findById(id).get());
    }


}
