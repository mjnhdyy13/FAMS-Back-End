package com.example.hcm23_java14_team2.service.Impl;

import com.example.hcm23_java14_team2.exception.ApplicationException;
import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.exception.ValidationException;
import com.example.hcm23_java14_team2.model.entities.User;
import com.example.hcm23_java14_team2.model.entities.UserPermission;
import com.example.hcm23_java14_team2.model.mapper.UserMapper;
import com.example.hcm23_java14_team2.model.request.User.AddUserRequest;
import com.example.hcm23_java14_team2.model.request.User.UserRequest;
import com.example.hcm23_java14_team2.model.response.*;
import com.example.hcm23_java14_team2.repository.UserPermissionRepository;
import com.example.hcm23_java14_team2.repository.UserRepository;
import com.example.hcm23_java14_team2.service.UserService;
import com.example.hcm23_java14_team2.util.ValidatorUtil;
import com.example.hcm23_java14_team2.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;


import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserPermissionRepository userPermissionRepository;

    private  final  UserMapper userMapper;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private ValidatorUtil validatorUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public PageResponse<List<UserResponse>> getAllUserWithPage(String search, Integer page, Integer size) {
        var PageUser = userRepository.searchByNameWithPage(search,PageRequest.of(page-1,size));
        List<UserResponse> userResponses = userMapper.toUserListResponses(PageUser.getContent());

        for(UserResponse userResponse : userResponses){
            UserPermission userPermission = userPermissionRepository.findById(userResponse.getRole_id()).get();
            userResponse.setRoleName(userPermission.getRoleName());
        }

        PageResponse<List<UserResponse>> listPageResponse = new PageResponse<>();
        listPageResponse.ok(userResponses);
        double total = Math.ceil((double)PageUser.getTotalElements() / size);
        listPageResponse.setTotalPage(total);
        return  listPageResponse;
    }

    @Override
    public ApiResponse<List<UserResponse>> getAllUser(String search) {
        var listUser = userRepository.searchByName(search);
        List<UserResponse> userResponses =userMapper.toUserListResponses(listUser);
        for(UserResponse userResponse : userResponses){
            UserPermission userPermission = userPermissionRepository.findById(userResponse.getRole_id()).get();
            userResponse.setRoleName(userPermission.getRoleName());
        }
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();
        apiResponse.ok(userResponses);
        return  apiResponse;
    }


    @Override
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user==null){
            throw new NotFoundException("");
        }

        UserResponse userResponse = userMapper.toResponse(user);
        userResponse.setRoleName(userPermissionRepository.findById(userResponse.getRole_id()).get().getRoleName());


        return userResponse;
    }

    @Transactional
    @Override
    public UserResponse updateUser(Long id,UserRequest userRequest, BindingResult bindingResult) {
        try{
            //Check is exist
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("User Not Found"));
            userValidator.validate(userRequest, bindingResult);
            //check valid
            userValidator.validate(userRequest,bindingResult);
            if (bindingResult.hasErrors()) {
                Map<String, String> validationErrors = validatorUtil.toErrors(bindingResult.getFieldErrors());
                throw new ValidationException(validationErrors);
            }

            if (userRequest.getName() != null) {
                existingUser.setName(userRequest.getName());
            }
            if (userRequest.getEmail() != null) {
                existingUser.setEmail(userRequest.getEmail());
            }
            if (userRequest.getPhone() != null) {
                existingUser.setPhone(userRequest.getPhone());
            }
            if (userRequest.getDateOfBirth() != null) {
                existingUser.setDateOfBirth(userRequest.getDateOfBirth());
            }
            if (userRequest.getGender() != '\0') {
                existingUser.setGender(userRequest.getGender());
            }
            if (userRequest.getStatusUser() != null) {
                existingUser.setStatusUser(userRequest.getStatusUser());
            }
            if (userRequest.getUserPermissionId() != null) {
                UserPermission userPermission = userPermissionRepository.findById(userRequest.getUserPermissionId()).orElseThrow(()->new NotFoundException(""));
                existingUser.setUserPermission(userPermission);
            }

            //save
            userRepository.saveAndFlush(existingUser);

            // Map to Response
            return userMapper.toResponse(existingUser);
        }
        catch (ApplicationException ex) {
            throw ex;
        }

    }

    @Transactional
    @Override
    public UserResponse deleteById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("User Not Found");
        }
        userRepository.deleteById(id);

        return userMapper.toResponse(user.get());
    }

    @Override
    public List<UserResponse> getUserListofPage(int page, int size) {
        PageRequest pr =PageRequest.of(page-1,size);
        List<User>  userList= userRepository.findAll(pr).getContent();
        return userMapper.toUserListResponses(userList);

    }

    @Override
    public long getTotalPage(long total, int size) {
        if(total % size ==0)
            return total / size ;
        return total / size + 1;

    }
    @Override
    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    public ApiResponse<Object> addUser(AddUserRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isEmpty()){
            var user = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .name(request.getName())
                    .phone(request.getPhone())
                    .dateOfBirth(request.getDateOfBirth())
                    .gender(request.getGender())
                    .statusUser(request.getStatusUser())
                    .userPermission(userPermissionRepository.findById(request.getRole_id()).get())
                    .build();
            try {
                userRepository.save(user);
                var response = ApiResponse
                        .builder()
                        .statusCode("200")
                        .message("Register successes")
                        .data(AuthenticationResponse.builder()
                                .email(user.getEmail())
                                .name(user.getName())
                                .role(user.getUserPermission().getRoleName().name())
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
}
