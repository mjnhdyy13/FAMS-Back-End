package com.example.hcm23_java14_team2.repository;

import com.example.hcm23_java14_team2.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.statusUser = 'ACTIVE' WHERE a.email = ?1")
    int enableUser(String email);
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByGmail(String email);

    @Query("select u from User u where u.name like %?1%")
    Page<User> searchByNameWithPage(String name, Pageable pageable);
    @Query("select u from User u where u.name like %?1%")
    List<User> searchByName(String name);

}
