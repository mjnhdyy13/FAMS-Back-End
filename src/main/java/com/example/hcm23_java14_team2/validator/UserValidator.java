package com.example.hcm23_java14_team2.validator;

import com.example.hcm23_java14_team2.model.entities.UserPermission;
import com.example.hcm23_java14_team2.model.request.User.UserRequest;
import com.example.hcm23_java14_team2.repository.UserPermissionRepository;
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
        UserRequest user = (UserRequest) target;

        if (user.getUserPermissionId() != null) {
            UserPermission userPermission = userPermissionRepository.findById(user.getUserPermissionId()).orElse(null);
            if (userPermission == null) {
                errors.rejectValue("userPermissionId", "error.userPermissionId", "UserPermission does not exist!");
            }
        }
    }
}
