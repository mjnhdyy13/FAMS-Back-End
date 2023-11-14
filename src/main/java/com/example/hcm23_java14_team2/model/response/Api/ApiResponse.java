package com.example.hcm23_java14_team2.model.response.Api;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private String statusCode;
    private String message;
    private T data;

    public void ok() {
        this.statusCode = "200";
        this.message="SUCCESS";
    }

    public void ok(T data) {
        this.statusCode = "200";
        this.message="SUCCESS";
        this.data = data;
    }

    public void notFound(){
        this.statusCode = "404";
        this.message="NOT FOUND";
    }

    public void notFound(String message){
        this.statusCode = "404";
        this.message=message;
    }
}
