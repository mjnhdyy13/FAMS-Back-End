package com.example.hcm23_java14_team2.service.Impl;

import com.example.hcm23_java14_team2.model.entities.ConfirmationToken;
import com.example.hcm23_java14_team2.model.entities.User;
import com.example.hcm23_java14_team2.repository.ConfirmationTokenRepository;
import com.example.hcm23_java14_team2.service.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    @Override
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    @Override
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    @Override
    public void setConfirmedAt(String token) {
        confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

    @Override
    public User getUser(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).get();
        return confirmationToken.getUser();
    }
}
