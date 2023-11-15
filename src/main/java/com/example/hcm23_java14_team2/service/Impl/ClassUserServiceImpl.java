package com.example.hcm23_java14_team2.service.Impl;

import com.example.hcm23_java14_team2.exception.ApplicationException;
import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.model.entities.*;
import com.example.hcm23_java14_team2.model.entities.Class;
import com.example.hcm23_java14_team2.model.response.Api.ApiResponse;
import com.example.hcm23_java14_team2.repository.ClassRepository;
import com.example.hcm23_java14_team2.repository.ClassUserRepository;
import com.example.hcm23_java14_team2.repository.UserRepository;
import com.example.hcm23_java14_team2.service.ClassUserService;
import io.jsonwebtoken.lang.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClassUserServiceImpl implements ClassUserService {
    @Autowired
    ClassUserRepository classUserRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ClassRepository classRepository;


    @Override
    public ApiResponse<Object> addUserClass(Long idClass, List<Long> idUser) {
        try {
            //Check Class
            Class existClass = classRepository.findById(idClass)
                    .orElseThrow(()-> new NotFoundException("Class Not Found"));
            //Load database to check
            List<Class_User> class_userListAll = classUserRepository.findAll();
            //Load to Set to check duplicate
            Set<String> uniqueClassUserIds = new HashSet<>();
            class_userListAll.forEach((i)->{
                String classUserId = i.getClassRoom().getId() + "-" + i.getUser().getId();
                uniqueClassUserIds.add(classUserId);
            });

            List<Class_User> class_userList = new ArrayList<Class_User>();
            //Load to List class_userList
            idUser.forEach((idusr) -> {
                //check User
                User user = userRepository.findById(idusr)
                        .orElseThrow(() -> new NotFoundException("User Not Found"));

                var classUser = Class_User.builder()
                        .classRoom(existClass)
                        .user(user).build();

                String classUserId = classUser.getClassRoom().getId() + "-" + user.getId();
                // Kiểm tra sự trùng lặp dựa trên class_id và user_id
                if (uniqueClassUserIds.add(classUserId)) {
                    // Nếu không trùng lặp, thêm vào danh sách
                    class_userList.add(classUser);
                }
            });
            classUserRepository.saveAll(class_userList);
            return ApiResponse.builder()
                    .statusCode("200")
                    .message("Add User to Class Complete")
                    .build();
        }
        catch (NotFoundException ex) {
            throw ex;
        }
        catch (Exception e){
            return ApiResponse.builder()
                    .statusCode("400")
                    .message("Add User to Class fails")
                    .build();
        }
    }

    @Override
    public ApiResponse<Object> deleteUserClass(Long idClass, List<Long> idUser) {
        try {
            //Check Class
            Class existClass = classRepository.findById(idClass)
                    .orElseThrow(()-> new NotFoundException("Class Not Found"));
            //Load database to check
            List<Class_User> class_userListAll = classUserRepository.findAll();

            List<Class_User> class_userList = new ArrayList<Class_User>();
            //Load to List class_userList
            idUser.forEach((idusr) -> {
                //check User
                User user = userRepository.findById(idusr)
                        .orElseThrow(() -> new NotFoundException("User Not Found"));
                //find Class_User in database and add to class_userList
                List<Class_User> existClassUser = classUserRepository.findByClassIdAndUserId(idClass,idusr);
                if (existClassUser.size()!=0) {
                    class_userList.addAll(existClassUser);
                }
            });
            if (class_userList.size() != 0) {
                classUserRepository.deleteAll(class_userList);
                return ApiResponse.builder()
                        .statusCode("200")
                        .message("Delete User to Class Complete")
                        .build();
            }
            return ApiResponse.builder()
                    .statusCode("404")
                    .message("Not found User in Class")
                    .build();
        }
        catch (NotFoundException ex) {
            throw ex;
        }
        catch (Exception e){
            return ApiResponse.builder()
                    .statusCode("400")
                    .message("Delete User to Class fails")
                    .build();
        }
    }

}
