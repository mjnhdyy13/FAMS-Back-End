package com.example.hcm23_java14_team2.model.request.Auth;


import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String name;
    @Length(min = 10,max = 11,message = "Phone must be min 10 and max 11 number")
    private String phone;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private String dateOfBirth;
    private char gender;
    private Long role_id;
}
