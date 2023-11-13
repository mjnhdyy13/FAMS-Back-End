package com.example.hcm23_java14_team2.service.Impl;

import com.example.hcm23_java14_team2.exception.ApplicationException;
import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.exception.ValidationException;
import com.example.hcm23_java14_team2.model.entities.Class;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusClass;
import com.example.hcm23_java14_team2.model.mapper.ClassMapper;
import com.example.hcm23_java14_team2.model.response.ClassResponse;
import com.example.hcm23_java14_team2.repository.ClassRepository;
import com.example.hcm23_java14_team2.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private ClassMapper classMapper;

    @Override
    public ClassResponse deleteByIdClass(Long id) {
        try {
            Class Class = classRepository.findById(id).orElse(null);
            if (Class == null) {
                throw new NotFoundException("Class Not Found");
            }
            // Update
            if (Class.getStatus() == StatusClass.PLANNING || Class.getStatus() == StatusClass.SCHEDULED )
            {
                Class.setStatus(StatusClass.DEACTIVE);
                classRepository.saveAndFlush(Class);
                return classMapper.toResponse(Class);
            }else {
                throw new ValidationException("Class In Running");
            }

        } catch (ApplicationException ex) {
            throw ex;
        }
    }
}
