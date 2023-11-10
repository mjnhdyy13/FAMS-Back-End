package com.example.hcm23_java14_team2.model.response;

import com.example.hcm23_java14_team2.model.entities.BaseEntity;
import com.example.hcm23_java14_team2.model.entities.Enum.Role;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusUser;
import com.example.hcm23_java14_team2.model.entities.User;
import lombok.*;


import java.util.Date;
@Getter
@Setter
@Builder
public class UserResponse extends BaseEntity {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String dateOfBirth;
    private char gender;
    private StatusUser status;
    private String createBy;
    private Date createDate;
    private String modifiedBy;
    private Date modifiedDate;
    //role
    private Long role_id;
    private Role roleName;
}
