package com.example.hcm23_java14_team2.validator;

import com.example.hcm23_java14_team2.model.entities.UserPermission;
import com.example.hcm23_java14_team2.model.request.User.UserRequest;
import com.example.hcm23_java14_team2.repository.UserPermissionRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserPermissionRepository userPermissionRepository;
    @Override
    public boolean supports(Class<?> clazz) {
        return UserRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRequest userRequest = (UserRequest) target;

        if (userRequest.getUserPermissionId() != null) {
            UserPermission userPermission = userPermissionRepository.findById(userRequest.getUserPermissionId()).orElse(null);
            if (userPermission == null) {
                errors.rejectValue("userPermissionId", "error.userPermissionId", "UserPermission does not exist!");
            }
            if (userRequest.getGender() != 'M' || userRequest.getGender() != 'F') {
                errors.rejectValue("gender", "error.gender", "Gender must be 'M' or 'F");
            }
            if (userRequest.getEmail() == null){
                errors.rejectValue("statusUser", "error.statusUser", "StatusUser must be 'ACTIVE' or 'INACTIVE'");
            }
            if (userRequest.getDateOfBirth() != null){
                DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);
                try {
                    sdf.parse(userRequest.getDateOfBirth());
                } catch (ParseException e) {
                    errors.rejectValue("date of birth", "error.date of birth", "Date of birth must be in the format dd/MM/yyyy");
                }
            }
        }
    }    
}
