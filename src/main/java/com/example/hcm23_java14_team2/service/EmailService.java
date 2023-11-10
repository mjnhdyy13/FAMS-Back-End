package com.example.hcm23_java14_team2.service;

public interface EmailService {
    // public String sendEmail(String to, String Subject, String body, MultipartFile[] file, String[] cc);
    void send(String to, String email);

    //To-do verify update password success
    //To-do verify reset password success
}
