package com.example.hcm23_java14_team2.repository;

import com.example.hcm23_java14_team2.model.entities.Class;
import com.example.hcm23_java14_team2.model.request.Class.ClassSearchRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.hcm23_java14_team2.model.request.Class.ClassRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
@Repository
public interface ClassRepository extends JpaRepository<Class,Long> {
    @Query("select c from Class c where c.className LIKE CONCAT('%',:#{#request.classNameOrCode}, '%') Or c.classCode LIKE CONCAT('%',:#{#request.classNameOrCode}, '%')")
    List<Class> searchByClassName(@Param("request") ClassSearchRequest request);

    @Query("SELECT c FROM Class c WHERE c.location IN :#{#request.locations} AND STR_TO_DATE(c.startDate, '%Y-%m-%d') >= STR_TO_DATE(:#{#request.fromDate}, '%Y-%m-%d') AND STR_TO_DATE(c.endDate, '%Y-%m-%d') <= STR_TO_DATE(:#{#request.toDate}, '%Y-%m-%d') AND c.status IN :#{#request.statuses} AND c.attendee IN :#{#request.attendee} AND c.FSU = :#{#request.fsu} AND c.trainingProgram.user IN (SELECT u FROM User u INNER JOIN u.userPermission p WHERE p.roleName = 'TRAINER' AND u.name = :#{#request.trainerName})")
    List<Class> findClasses(@Param("request") ClassSearchRequest request);
}
