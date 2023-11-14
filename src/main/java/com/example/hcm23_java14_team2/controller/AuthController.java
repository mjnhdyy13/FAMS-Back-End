package com.example.hcm23_java14_team2.controller;

import com.example.hcm23_java14_team2.model.request.Auth.AuthRequest;
import com.example.hcm23_java14_team2.model.request.Auth.ChangePasswordRequest;
import com.example.hcm23_java14_team2.model.request.Auth.RegisterRequest;
import com.example.hcm23_java14_team2.model.request.Auth.RequireTokenToChangePw;
import com.example.hcm23_java14_team2.service.AuthenticationService;
import com.example.hcm23_java14_team2.service.ResetPasswordService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthenticationService authenticationService;
    private final ResetPasswordService resetPasswordService;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to my website";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.OK);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request){
        return new ResponseEntity<>(authenticationService.authenticate(request),HttpStatus.OK);
    }
    @GetMapping( "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return authenticationService.confirmToken(token);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> requireResetPassword(@RequestBody RequireTokenToChangePw request) {
       return new ResponseEntity<>(resetPasswordService.sendEmail(request.getEmail()),HttpStatus.OK);
    }
    @PostMapping("/confirm-password")
    public ResponseEntity<?> confirmResetPassword(@RequestBody ChangePasswordRequest request) {
        return new ResponseEntity<>(resetPasswordService.confirmResetPassword(request.getToken(), request.getNewPassword()),HttpStatus.OK);
    }
}
