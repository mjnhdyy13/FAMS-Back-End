package com.example.hcm23_java14_team2.service;

import com.example.hcm23_java14_team2.model.entities.ConfirmationToken;
import com.example.hcm23_java14_team2.model.entities.User;

import java.util.Optional;


public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken token);
    Optional<ConfirmationToken> getToken(String token);

    void setConfirmedAt(String token);

    User getUser(String token);
}
