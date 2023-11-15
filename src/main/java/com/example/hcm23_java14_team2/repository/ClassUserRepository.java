package com.example.hcm23_java14_team2.repository;

import com.example.hcm23_java14_team2.model.entities.Class_User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassUserRepository extends JpaRepository<Class_User, Long> {
    @Query(value = "SELECT c FROM Class_User c WHERE c.classRoom.id = ?1 and c.user.id = ?2")
    List<Class_User> findByClassIdAndUserId(Long idClass, Long idUser);
}
