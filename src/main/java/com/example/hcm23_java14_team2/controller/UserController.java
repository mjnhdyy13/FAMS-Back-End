package com.example.hcm23_java14_team2.controller;

import com.example.hcm23_java14_team2.exception.ApplicationException;
import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.exception.ValidationException;
import com.example.hcm23_java14_team2.model.mapper.UserMapper;
import com.example.hcm23_java14_team2.model.request.User.AddUserRequest;
import com.example.hcm23_java14_team2.model.request.User.UserRequest;
import com.example.hcm23_java14_team2.model.response.ApiResponse;
import com.example.hcm23_java14_team2.model.response.UserResponse;
import com.example.hcm23_java14_team2.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/list")
    public ResponseEntity<?> getAllUser(@RequestParam(value = "search",defaultValue = "") String search,
                                        @RequestParam(value = "page",required = false) Integer  page,
                                        @RequestParam(value = "size",defaultValue = "2") Integer  size) {
        try{
            if(page!= null)
                return new ResponseEntity<>(userService.getAllUserWithPage(search,page,size),HttpStatus.OK);
            return new ResponseEntity<>(userService.getAllUser(search),HttpStatus.OK);
        }catch (Exception e){
            throw new ApplicationException();
        }
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getByIdByPathVariable(@PathVariable Long id) {
        try {
            ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
            apiResponse.ok(userService.findById(id));
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex; // Rethrow NotFoundException
        } catch (Exception ex) {
            throw new ApplicationException(); // Handle other exceptions
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable  Long id,@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
        try {
            UserResponse userResponse = userService.updateUser(id, userRequest, bindingResult);
            ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
            apiResponse.ok(userResponse);
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
            apiResponse.ok(userService.deleteById(id));
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex; // Rethrow NotFoundException
        } catch (Exception ex) {
            throw new ApplicationException(); // Handle other exceptions
        }
    }

    @PostMapping("/add-user")
    public ResponseEntity<?> addUser(@RequestBody @Valid AddUserRequest request) {
        try{
            return new ResponseEntity<>(userService.addUser(request),HttpStatus.OK);
        }catch (Exception e){
            throw new ApplicationException();
        }
    }

}
