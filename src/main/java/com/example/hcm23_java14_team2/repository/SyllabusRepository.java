package com.example.hcm23_java14_team2.repository;

import com.example.hcm23_java14_team2.model.entities.Syllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SyllabusRepository extends JpaRepository<Syllabus,Long> {
    
}

