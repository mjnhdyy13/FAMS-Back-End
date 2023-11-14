package com.example.hcm23_java14_team2.model.request.Auth;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequireTokenToChangePw {
    private String email;
}
