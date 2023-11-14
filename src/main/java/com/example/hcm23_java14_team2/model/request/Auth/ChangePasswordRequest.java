package com.example.hcm23_java14_team2.model.request.Auth;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {
    private String token;
    private String newPassword;
}
