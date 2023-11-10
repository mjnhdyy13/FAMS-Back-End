package com.example.hcm23_java14_team2.repository;

import com.example.hcm23_java14_team2.model.entities.OutputStandard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutputStandardRepository extends JpaRepository<OutputStandard,Long> {
    // @Query(value = "select os " + 
    //     "from OutputStandard os " + 
    //     "join os.syllabus_standardList ss " + 
    //     // "join ss.syllabus s "+
    //     "where ss.syllabus.id = ?1")
    @Query(value = "select ss.outputStandard " +
        "from Syllabus_Standard ss " +
        "where ss.syllabus.id = ?1")
    List<OutputStandard> findOutputStandardBySyllabusId(Long id);
}

