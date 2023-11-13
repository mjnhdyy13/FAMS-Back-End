package com.example.hcm23_java14_team2.model.request.User;

import com.example.hcm23_java14_team2.model.entities.Enum.StatusUser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Builder
@Getter
@Setter
public class UserRequest implements Serializable {
    private String name;
    private String email;
    private String phone;
    private String dateOfBirth;
    private char gender;
    private StatusUser statusUser;
    private Long userPermissionId;
}

