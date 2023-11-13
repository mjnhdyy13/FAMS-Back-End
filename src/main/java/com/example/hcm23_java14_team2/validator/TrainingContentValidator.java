package com.example.hcm23_java14_team2.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.hcm23_java14_team2.model.entities.User;
import com.example.hcm23_java14_team2.model.request.TrainingContent.TrainingContentRequest;
import com.example.hcm23_java14_team2.repository.UserRepository;


//To-do
@Component
public class TrainingContentValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return TrainingContentRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }
}