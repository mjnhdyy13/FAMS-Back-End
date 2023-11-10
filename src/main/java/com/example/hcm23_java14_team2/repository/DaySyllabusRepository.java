package com.example.hcm23_java14_team2.repository;

import com.example.hcm23_java14_team2.model.entities.DaySyllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DaySyllabusRepository extends JpaRepository<DaySyllabus,Long> {
    @Query(value = "select ss " +
            "from DaySyllabus ss " +
            "where ss.syllabus.id = ?1 and ss.day =?2")
    Optional<DaySyllabus> findBySyllabusIdAndDayNum(Long id, Integer day);

    @Query(value = "select ss " +
            "from DaySyllabus ss " +
            "where ss.syllabus.id = ?1 order by ss.day")
    List<DaySyllabus> findBySyllabusId(Long syllabus_id);

}
