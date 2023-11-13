package com.example.hcm23_java14_team2.model.entities;

import com.example.hcm23_java14_team2.model.entities.Enum.Permission;
import com.example.hcm23_java14_team2.model.entities.Enum.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_permission")
public class UserPermission  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private Role roleName;

    @Column(name = "syllabus")
    @Enumerated(EnumType.STRING)
    private Permission syllabus;

    @Column(name = "training_program")
    @Enumerated(EnumType.STRING)
    private Permission trainingProgram; //(trainingProgram.FULL_ACCESS)

    @Column(name = "class_room")
    @Enumerated(EnumType.STRING)
    private Permission classRoom;

    @Column(name = "learning_material")
    @Enumerated(EnumType.STRING)
    private Permission learningMaterial;

    @Column(name = "user")
    @Enumerated(EnumType.STRING)
    private Permission user;

    @OneToMany(mappedBy = "userPermission", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> userList;
}
