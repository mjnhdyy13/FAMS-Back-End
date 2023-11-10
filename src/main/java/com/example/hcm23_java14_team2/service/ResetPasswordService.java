package com.example.hcm23_java14_team2.service;

import com.example.hcm23_java14_team2.model.response.ApiResponse;

public interface ResetPasswordService {
    ApiResponse<Object> sendEmail(String email);
    String buildEmail(String name, String link);
    ApiResponse<Object> confirmResetPassword(String token, String newPassword);
}
