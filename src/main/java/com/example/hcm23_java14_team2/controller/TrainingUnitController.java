package com.example.hcm23_java14_team2.controller;

import com.example.hcm23_java14_team2.model.request.TrainingUnit.TrainingUnitRequest;
import com.example.hcm23_java14_team2.model.request.TrainingUnit.UpdateUnitNameRequest;
import com.example.hcm23_java14_team2.service.TrainingUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/training-unit")
public class TrainingUnitController {
    private final TrainingUnitService trainingUnitService;
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody TrainingUnitRequest request) {
        return new ResponseEntity<>(trainingUnitService.add(request), HttpStatus.OK);
    }
    @GetMapping("/fetch")
    public ResponseEntity<?> fetchBySyllabus(@RequestParam(value = "id",defaultValue = "") Long id) {
        return new ResponseEntity<>(trainingUnitService.fetchDayAndUnit(id),HttpStatus.OK);
    }
    @PostMapping("/add-day")
    public ResponseEntity<?> addDayInSyllabus(@RequestParam(value = "id",defaultValue = "") Long id,
        @RequestParam(value = "day",defaultValue = "") Integer day) {
        return new ResponseEntity<>(trainingUnitService.addDayInSyllabus(id,day),HttpStatus.OK);
    }

    @PutMapping("/update/unit-name")
    public ResponseEntity<?> updateUnitName(@RequestBody UpdateUnitNameRequest request) {
        return new ResponseEntity<>(trainingUnitService.updateUnitName(request.getUnit_id(),request.getName()),HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUnit(@RequestParam(value = "unit_id",defaultValue = "") Long unit_id) {
        return new ResponseEntity<>(trainingUnitService.deleteUnit(unit_id),HttpStatus.OK);
    }
    @DeleteMapping("/delete-day")
    public ResponseEntity<?> deleteDay(@RequestParam(value = "id",defaultValue = "") Long id,
                                       @RequestParam(value = "day",defaultValue = "") Integer day) {
        return new ResponseEntity<>(trainingUnitService.deleteDay(id, day),HttpStatus.OK);
    }
    @GetMapping("/fetch-unit")
    public ResponseEntity<?> fetchUnit(@RequestParam(value = "unit_id",defaultValue = "") Long unit_id) {
        return new ResponseEntity<>(trainingUnitService.fetch1Unit(unit_id),HttpStatus.OK);
    }
}
