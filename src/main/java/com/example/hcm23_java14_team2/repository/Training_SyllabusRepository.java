package com.example.hcm23_java14_team2.repository;

import com.example.hcm23_java14_team2.model.entities.Training_Syllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Training_SyllabusRepository extends JpaRepository<Training_Syllabus, Long> {
    @Query(value = "SELECT t FROM Training_Syllabus t WHERE t.trainingProgram.id = ?1 and t.syllabus.id = ?2")
    Optional<Training_Syllabus> findByTrainingIdAndSyllabusId(Integer idTraining, Long idSyllabus);
}
