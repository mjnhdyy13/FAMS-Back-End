package com.example.hcm23_java14_team2.model.request.User;

import com.example.hcm23_java14_team2.model.entities.Enum.StatusUser;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUserRequest {
    private String email;
    private String password;
    private String name;
    //@Length(min = 10,max = 11,message = "Phone must be min 10 and max 11 number")
    private String phone;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private String dateOfBirth;
    private char gender;
    private StatusUser statusUser;
    private Long role_id;
}