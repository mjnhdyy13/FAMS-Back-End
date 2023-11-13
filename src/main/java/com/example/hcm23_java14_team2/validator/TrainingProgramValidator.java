package com.example.hcm23_java14_team2.validator;
import com.example.hcm23_java14_team2.model.entities.User;
import com.example.hcm23_java14_team2.model.request.TrainingProgram.InsertTrainingProgramRequest;
import com.example.hcm23_java14_team2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class TrainingProgramValidator implements Validator {
    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean supports(Class<?> clazz) {
        return InsertTrainingProgramRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // InsertTrainingProgramRequest trainingProgramRequest = (InsertTrainingProgramRequest) target;

        // if (trainingProgramRequest.getUserId() != null) {
        //     User user = userRepository.findById(trainingProgramRequest.getUserId()).orElse(null);
        //     if (user == null) {
        //         errors.rejectValue("userPermissionId", "error.userPermissionId", "UserPermission does not exist!");
        //     }
        // }
    }
}
