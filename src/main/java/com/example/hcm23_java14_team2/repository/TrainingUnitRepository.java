package com.example.hcm23_java14_team2.repository;

import com.example.hcm23_java14_team2.model.entities.OutputStandard;
import com.example.hcm23_java14_team2.model.entities.TrainingUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingUnitRepository extends JpaRepository<TrainingUnit,Long> {
    @Query(value = "select ss " +
            "from TrainingUnit ss " +
            "where ss.daySyllabus.id = ?1 and ss.unitNum =?2")
    Optional<TrainingUnit> findBySyllabusAndUnitNum(Long day_id, Integer unitNum);
    @Query(value = "select ss " +
            "from TrainingUnit ss " +
            "where ss.daySyllabus.id = ?1 order by ss.unitNum")
    List<TrainingUnit> findAllByDayId(Long day_id);
}
