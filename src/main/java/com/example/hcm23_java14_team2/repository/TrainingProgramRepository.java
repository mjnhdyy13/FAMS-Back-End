package com.example.hcm23_java14_team2.repository;

import com.example.hcm23_java14_team2.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hcm23_java14_team2.model.entities.TrainingProgram;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Integer> {
    @Query("select t from TrainingProgram t where t.name like %?1%")
    Page<TrainingProgram> searchByNameWithPage(String name, Pageable pageable);

    @Query("select t from TrainingProgram t where t.name like %?1%")
    List<TrainingProgram> searchByName(String name);
    
}
