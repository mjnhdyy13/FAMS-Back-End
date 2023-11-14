package com.example.hcm23_java14_team2.service.Impl;

import com.example.hcm23_java14_team2.model.entities.ConfirmationToken;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusUser;
import com.example.hcm23_java14_team2.model.entities.User;
import com.example.hcm23_java14_team2.model.request.Auth.AuthRequest;
import com.example.hcm23_java14_team2.model.request.Auth.RegisterRequest;
import com.example.hcm23_java14_team2.model.response.Api.ApiResponse;
import com.example.hcm23_java14_team2.model.response.Auth.AuthenticationResponse;
import com.example.hcm23_java14_team2.repository.UserPermissionRepository;
import com.example.hcm23_java14_team2.repository.UserRepository;
import com.example.hcm23_java14_team2.security.JwtService;
import com.example.hcm23_java14_team2.service.AuthenticationService;
import com.example.hcm23_java14_team2.service.ConfirmationTokenService;
import com.example.hcm23_java14_team2.service.EmailService;
import com.example.hcm23_java14_team2.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserPermissionRepository userPermissionRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final EmailValidatorImpl emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final UserService userService;
    @Override
    public ApiResponse<Object> register(RegisterRequest request) {
        boolean isValidEmail = emailValidator.
                test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        if(userRepository.findByEmail(request.getEmail()).isEmpty()){
            var user = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .name(request.getName())
                    .phone(request.getPhone())
                    .dateOfBirth(request.getDateOfBirth())
                    .gender(request.getGender())
                    .statusUser(StatusUser.DEACTIVE)
                    .userPermission(userPermissionRepository.findById(request.getRole_id()).get())
                    .build();
            try {
                userRepository.save(user);
                String token = UUID.randomUUID().toString();

                ConfirmationToken confirmationToken = new ConfirmationToken(
                        token,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(15),
                        user
                );
                String link = "http://localhost:8080/api/v1/auth/confirm?token=" + token;
                confirmationTokenService.saveConfirmationToken(
                        confirmationToken);
                //send verify email to user after register
                emailService.send(user.getEmail(),buildEmail(request.getEmail(), link));

                var jwtToken = jwtService.generateToken(user);
                var response = ApiResponse
                        .builder()
                        .statusCode("200")
                        .message("Register successes")
                        .data(AuthenticationResponse.builder()
                                .email(user.getEmail())
                                .name(user.getName())
                                .role(user.getUserPermission().getRoleName().name())
                                .accessToken(jwtToken)
                                .build())
                        .build();
                return response;
            }
            catch (Exception e) {
                return ApiResponse
                        .builder()
                        .statusCode("401")
                        .message("Register failed")
                        .build();
            }
        } else {
            return ApiResponse
                    .builder()
                    .statusCode("401")
                    .message("User existed")
                    .build();
        }
    }

    @Override
    public ApiResponse<Object> authenticate(AuthRequest request) {
        if(checkUser(request)) {
            var user = userRepository.findByEmail(request.getEmail());
            var jwtToken = jwtService.generateToken(user.get());
            var response = ApiResponse
                    .builder()
                    .statusCode("200")
                    .message("Login successes")
                    .data(
                            AuthenticationResponse.builder()
                                    .email(user.get().getEmail())
                                    .name(user.get().getName())
                                    .role(user.get().getUserPermission().getRoleName().name())
                                    .accessToken(jwtToken)
                                    .build())
                    .build();
            return response;
        } else {
            return ApiResponse
                    .builder()
                    .statusCode("401")
                    .message("Login failed - Incorrect email or password")
                    .build();
        }
    }

    @Override
    public boolean checkUser(AuthRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            var user = userRepository.findByEmail(request.getEmail());
            String password = user.get().getPassword();
            if(passwordEncoder.matches(request.getPassword(),password)){
                return true;
            }
        };
        return false;
    }
    @Transactional
    @Override
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(
                confirmationToken.getUser().getEmail());
        return "confirmed";
    }

    @Override
    public String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}

