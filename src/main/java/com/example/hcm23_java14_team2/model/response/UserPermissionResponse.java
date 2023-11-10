package com.example.hcm23_java14_team2.model.response;

import com.example.hcm23_java14_team2.model.entities.Enum.Permission;
import com.example.hcm23_java14_team2.model.entities.Enum.Role;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserPermissionResponse {
    private Long id;
    private Role roleName;
    private Permission syllabus;
    private Permission trainingProgram;
    private Permission classRoom;
    private Permission learningMaterial;
    private Permission user;
}
