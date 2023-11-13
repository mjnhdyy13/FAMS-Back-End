package com.example.hcm23_java14_team2.service.Impl;


import com.example.hcm23_java14_team2.model.entities.DaySyllabus;
import com.example.hcm23_java14_team2.model.entities.Syllabus;
import com.example.hcm23_java14_team2.model.entities.TrainingContent;
import com.example.hcm23_java14_team2.model.entities.TrainingUnit;
import com.example.hcm23_java14_team2.model.request.TrainingUnit.TrainingUnitRequest;
import com.example.hcm23_java14_team2.model.response.ApiResponse;
import com.example.hcm23_java14_team2.model.response.DayResponse;
import com.example.hcm23_java14_team2.repository.DaySyllabusRepository;
import com.example.hcm23_java14_team2.repository.SyllabusRepository;
import com.example.hcm23_java14_team2.repository.TrainingContentRepository;
import com.example.hcm23_java14_team2.repository.TrainingUnitRepository;
import com.example.hcm23_java14_team2.service.TrainingUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class TrainingUnitServiceImpl implements TrainingUnitService {
    @Autowired
    TrainingUnitRepository trainingUnitRepository;
    @Autowired
    SyllabusRepository syllabusRepository;
    @Autowired
    TrainingContentRepository trainingContentRepository;
    @Autowired
    DaySyllabusRepository daySyllabusRepository;
    @Override
    public ApiResponse<Object> add(TrainingUnitRequest request) {
        if(checkExist(request.getDay_id(),request.getUnitNum())) {
            var unit = TrainingUnit.builder()
                    .unitName(request.getUnitName())
                    .daySyllabus(daySyllabusRepository.findById(request.getDay_id()).get())
                    .unitNum(request.getUnitNum())
                    .build();
            try {
                trainingUnitRepository.save(unit);
                var data = fetch(request.getSyllabus_id());
                return ApiResponse.builder()
                        .statusCode("200")
                        .message("Add unit successfully")
                        .data(data)
                        .build();
            } catch (Exception e) {
                return ApiResponse.builder()
                        .statusCode("400")
                        .data(e)
                        .message("Add unit failed")
                        .build();
            }
        }
        return ApiResponse.builder()
                .statusCode("400")
                .message("Num of unit in this day is existed")
                .build();
    }
    public List<DayResponse> fetch(Long syllabus_id) {
        List<DayResponse> dayResponses = new ArrayList<>();
        List<DaySyllabus> daySyllabusList = daySyllabusRepository.findBySyllabusId(syllabus_id);
        for(DaySyllabus daySyllabus:daySyllabusList) {
            //List<TrainingUnit> trainingUnitList = trainingUnitRepository.findAllByDayId(daySyllabus.getId());
            var build = DayResponse.builder()
                    .day_id(daySyllabus.getId())
                    .day_number(daySyllabus.getDay())
                    .trainingUnit(trainingUnitRepository.findAllByDayId(daySyllabus.getId()))
                    .build();
            dayResponses.add(build);
        }
        return dayResponses;
    }
    @Override
    public ApiResponse<Object> fetchDayAndUnit(Long syllabus_id) {
        try {
            var data = fetch(syllabus_id);
            return ApiResponse.builder()
                    .message("Successfully")
                    .statusCode("200")
                    .data(data)
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .message("Failed")
                    .statusCode("400")
                    .build();
        }

    }

    @Override
    public ApiResponse<Object> addDayInSyllabus(Long syllabus_id,Integer day_number) {
        try {
            if(daySyllabusRepository.findBySyllabusIdAndDayNum(syllabus_id,day_number).isPresent()) {
                return ApiResponse.builder()
                        .statusCode("400")
                        .message("Day is existed")
                        .build();
            }
            var day = DaySyllabus.builder()
                    .day(day_number)
                    .syllabus(syllabusRepository.findById(syllabus_id).get())
                    .build();
            daySyllabusRepository.save(day);
            var data = fetch(syllabus_id);
            return ApiResponse.builder()
                    .statusCode("200")
                    .data(data)
                    .message("Add day successfully!!")
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .statusCode("400")
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ApiResponse<Object> updateUnitName(Long unit_id,String name) {
        try {
            TrainingUnit trainingUnit = trainingUnitRepository.findById(unit_id).get();
            trainingUnit.setUnitName(name);
            trainingUnitRepository.saveAndFlush(trainingUnit);
            DaySyllabus daySyllabus = daySyllabusRepository.findById(trainingUnit.getDaySyllabus().getId()).get();
            var data = fetch(daySyllabus.getSyllabus().getId());
            return ApiResponse.builder()
                    .statusCode("200")
                    .data(data)
                    .message("Update name successfully!")
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .statusCode("400")
                    .message("Update name failed!")
                    .build();
        }

    }

    @Override
    public ApiResponse<Object> deleteUnit(Long unit_id) {
        try {
            TrainingUnit trainingUnit = trainingUnitRepository.findById(unit_id).get();
            trainingUnitRepository.delete(trainingUnit);
            DaySyllabus daySyllabus = daySyllabusRepository.findById(trainingUnit.getDaySyllabus().getId()).get();
            var data = fetch(daySyllabus.getSyllabus().getId());
            return ApiResponse.builder()
                    .statusCode("200")
                    .message("Delete successfully!")
                    .data(data)
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .statusCode("400")
                    .message("Delete failed!")
                    .build();
        }
    }

    @Override
    public ApiResponse<Object> deleteDay(Long syllabus_id,Integer day) {
        try {
            DaySyllabus daySyllabus = daySyllabusRepository.findBySyllabusIdAndDayNum(syllabus_id,day).get();
            List<TrainingUnit> trainingUnitList = trainingUnitRepository.findAllByDayId(daySyllabus.getId());
            trainingUnitRepository.deleteAll(trainingUnitList);
            daySyllabusRepository.delete(daySyllabus);
            var data = fetch(syllabus_id);
            return ApiResponse.builder()
                        .statusCode("200")
                        .message("Delete successfully!!")
                        .data(data)
                        .build();

        } catch (Exception e) {
            return ApiResponse.builder()
                    .statusCode("400")
                    .message("Delete failed!")
                    .build();
        }

    }

    @Override
    public ApiResponse<Object> fetch1Unit(Long id) {
        try {
            var data = trainingUnitRepository.findById(id);
            return ApiResponse.builder()
                    .statusCode("200")
                    .data(data)
                    .message("Successfully!")
                    .build();
        } catch (Exception e){
            return ApiResponse.builder()
                    .statusCode("400")
                    .message("Failed!")
                    .build();
        }
    }

    public boolean checkExist(Long day_id, Integer num) {
        if(trainingUnitRepository.findBySyllabusAndUnitNum(day_id, num).isPresent()) {
            return false;
        }
        return true;
    }
}
