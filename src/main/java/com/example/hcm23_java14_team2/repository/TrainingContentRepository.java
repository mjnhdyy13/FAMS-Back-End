package com.example.hcm23_java14_team2.repository;

import com.example.hcm23_java14_team2.model.entities.TrainingUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hcm23_java14_team2.model.entities.TrainingContent;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainingContentRepository extends JpaRepository<TrainingContent, Long> {
    @Query(value = "select ss " +
            "from TrainingContent ss " +
            "where ss.trainingUnit.id = ?1")
    List<TrainingContent> findAllByTrainingUnitId(Long id);
}