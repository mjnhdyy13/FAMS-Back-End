package com.example.hcm23_java14_team2.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "class__user")
public class Class_User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "class_id")
    private Class classRoom;

}
