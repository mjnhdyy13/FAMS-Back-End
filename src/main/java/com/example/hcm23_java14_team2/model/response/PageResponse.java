package com.example.hcm23_java14_team2.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T>  extends  ApiResponse<T> {
    private double totalPage;
}
