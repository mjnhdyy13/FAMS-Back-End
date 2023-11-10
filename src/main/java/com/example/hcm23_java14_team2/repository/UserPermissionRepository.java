package com.example.hcm23_java14_team2.repository;

import com.example.hcm23_java14_team2.model.entities.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission,Long> {
}
