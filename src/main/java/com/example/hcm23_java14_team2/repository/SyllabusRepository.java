package com.example.hcm23_java14_team2.repository;

import com.example.hcm23_java14_team2.model.entities.Syllabus;
import com.example.hcm23_java14_team2.model.entities.TrainingProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SyllabusRepository extends JpaRepository<Syllabus,Long> {
    @Query("select s from Syllabus s where s.topicName like %?1%")
    Page<Syllabus> searchByNameWithPage(String name, Pageable pageable);

    @Query("select s from Syllabus s where s.topicName like %?1%")
    List<Syllabus> searchByName(String name);
}

