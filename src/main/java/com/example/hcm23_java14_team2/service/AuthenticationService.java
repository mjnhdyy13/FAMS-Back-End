package com.example.hcm23_java14_team2.service;

import com.example.hcm23_java14_team2.model.request.Auth.AuthRequest;
import com.example.hcm23_java14_team2.model.request.Auth.RegisterRequest;
import com.example.hcm23_java14_team2.model.response.ApiResponse;

public interface AuthenticationService {
    ApiResponse<Object> register(RegisterRequest request);

    ApiResponse<Object> authenticate(AuthRequest request);

    boolean checkUser(AuthRequest request);
    String confirmToken(String token);
    String buildEmail(String name, String link);
}
