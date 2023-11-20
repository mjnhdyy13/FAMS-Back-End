package com.example.hcm23_java14_team2.model.request.UserPermission;

import com.example.hcm23_java14_team2.model.entities.Enum.Permission;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditPermissionRequest {
    private Long id;
    private Permission syllabus;
    private Permission trainingProgram;
    private Permission classRoom;
    private Permission learningMaterial;
}
