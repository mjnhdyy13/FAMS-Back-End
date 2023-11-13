package com.example.hcm23_java14_team2.service;

import com.example.hcm23_java14_team2.model.request.TrainingUnit.TrainingUnitRequest;
import com.example.hcm23_java14_team2.model.response.ApiResponse;

public interface TrainingUnitService {
    ApiResponse<Object> add(TrainingUnitRequest request);
    ApiResponse<Object> fetchDayAndUnit(Long syllabus_id);
    ApiResponse<Object> addDayInSyllabus(Long syllabus_id,Integer day_number);
    ApiResponse<Object> updateUnitName(Long id,String name);
    ApiResponse<Object> deleteUnit(Long id);
    ApiResponse<Object> deleteDay(Long id, Integer day);
    ApiResponse<Object> fetch1Unit(Long id);
}
